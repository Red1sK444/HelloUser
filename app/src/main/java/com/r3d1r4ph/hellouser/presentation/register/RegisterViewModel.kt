package com.r3d1r4ph.hellouser.presentation.register

import android.text.Editable
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.r3d1r4ph.domain.common.exceptions.*
import com.r3d1r4ph.domain.common.validation.ValidationRule
import com.r3d1r4ph.domain.entities.RegisterEntity
import com.r3d1r4ph.domain.usecases.GetUserUseCase
import com.r3d1r4ph.domain.usecases.RegisterUseCase
import com.r3d1r4ph.domain.usecases.ValidateInputFieldUseCase
import com.r3d1r4ph.hellouser.R
import com.r3d1r4ph.hellouser.presentation.common.ui.Event
import com.r3d1r4ph.hellouser.presentation.register.model.RegisterAction
import com.r3d1r4ph.hellouser.presentation.register.model.RegisterInputFieldEnum
import com.r3d1r4ph.hellouser.presentation.register.model.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val validateInputFieldUseCase: ValidateInputFieldUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(RegisterUiState())
    val uiState: LiveData<RegisterUiState>
        get() = _uiState.map { it }

    private val _uiAction = MutableLiveData<Event<RegisterAction>>()
    val uiAction: LiveData<Event<RegisterAction>>
        get() = _uiAction.map { it }

    init {
        checkIsUserAlreadyRegistered()
    }

    private fun checkIsUserAlreadyRegistered() {
        viewModelScope.launch {
            getUserUseCase.execute()
                .onSuccess {
                    _uiAction.value = Event(RegisterAction.OpenMainScreen)
                }
        }
    }

    fun tryToRegister(
        name: String,
        surname: String,
        dateOfBirth: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {

            val inputFieldResults = listOf(
                validateInputFieldUseCase.execute(ValidationRule.IsNameOrSurname(name)),
                validateInputFieldUseCase.execute(ValidationRule.IsNameOrSurname(surname)),
                validateInputFieldUseCase.execute(ValidationRule.IsBirthDate(dateOfBirth)),
                validateInputFieldUseCase.execute(ValidationRule.IsPassword(password)),
                validateInputFieldUseCase.execute(ValidationRule.IsPassword(confirmPassword))
            )

            val hasError = inputFieldResults.any { it.isFailure }

            if (hasError) {
                updateInputFieldErrors(inputFieldResults)
            } else {
                register(
                    RegisterEntity(
                        name = name,
                        surname = surname,
                        birthDate = dateOfBirth,
                        password = password,
                        confirmPassword = confirmPassword
                    )
                )
            }
        }
    }

    private fun updateInputFieldErrors(inputFieldResults: List<Result<Unit>>) {
        val inputFieldErrorMessages =
            inputFieldResults.map { result -> getErrorMessageByValidationResult(result) }

        _uiState.value = RegisterUiState(
            inputFieldErrors = RegisterInputFieldEnum.values()
                .zip(inputFieldErrorMessages)
                .toMap()
        )
    }

    @StringRes
    private fun getErrorMessageByValidationResult(validationResult: Result<Unit>): Int? {
        validationResult
            .onSuccess {
                return null
            }
            .onFailure {
                return when (it) {
                    is TextLengthLessThanMinimalException -> R.string.minimal_length_x_symbols
                    is FirstCharNoUpperCaseException -> R.string.first_char_no_upper_case
                    is NoUpperCaseException -> R.string.at_least_one_symbol_has_to_be_in_upper_case
                    is NoLowerCaseException -> R.string.at_least_one_symbol_has_to_be_in_lower_case
                    is NoDigitException -> R.string.no_digit
                    else -> R.string.unknown_error
                }
            }

        return R.string.unknown_error
    }

    private suspend fun register(registerEntity: RegisterEntity) {
        registerUseCase.execute(registerEntity)
            .onSuccess {
                _uiAction.value = Event(RegisterAction.OpenMainScreen)
            }
            .onFailure { throwable ->
                _uiAction.value = Event(
                    RegisterAction.Error(
                        when (throwable) {
                            is PasswordNotEqualWithConfirmPasswordException -> R.string.passwords_are_not_equal
                            is RegisterUserException -> R.string.register_user_error
                            else -> R.string.unknown_error
                        }
                    )
                )
            }
    }

    fun dismissIfNotBlankOrSetEmptyError(
        inputFieldText: Editable?,
        registerInputFieldEnum: RegisterInputFieldEnum
    ) {
        inputFieldText?.let { value ->
            if (value.isNotBlank()) {
                dismissInputFieldError(
                    registerInputFieldEnum
                )
            } else {
                setEmptyFieldError(
                    registerInputFieldEnum
                )
            }
        }
    }

    private fun dismissInputFieldError(inputFieldToDismiss: RegisterInputFieldEnum) {
        viewModelScope.launch {
            _uiState.value?.let {
                _uiState.value =
                    it.copy(
                        inputFieldErrors = it.inputFieldErrors.keys.associateWith { inputFieldEnum ->
                            if (inputFieldEnum == inputFieldToDismiss) null
                            else it.inputFieldErrors[inputFieldEnum]
                        }
                    )
            }
        }
    }

    private fun setEmptyFieldError(inputFieldToDismiss: RegisterInputFieldEnum) {
        viewModelScope.launch {
            _uiState.value?.let {
                _uiState.value =
                    it.copy(
                        inputFieldErrors = it.inputFieldErrors.keys.associateWith { inputFieldEnum ->
                            if (inputFieldEnum == inputFieldToDismiss) R.string.empty_field
                            else it.inputFieldErrors[inputFieldEnum]
                        }
                    )
            }
        }
    }
}