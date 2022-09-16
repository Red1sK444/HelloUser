package com.r3d1r4ph.hellouser.presentation.register

import android.text.Editable
import androidx.lifecycle.*
import com.r3d1r4ph.hellouser.R
import com.r3d1r4ph.hellouser.presentation.common.ui.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableLiveData(RegisterUiState())
    val uiState: LiveData<RegisterUiState>
        get() = _uiState.map { it }

    private val _uiAction = MutableLiveData<Event<RegisterAction>>()
    val uiAction: LiveData<Event<RegisterAction>>
        get() = _uiAction.map { it }

    fun dismissIfNotBlankOrSetEmptyError(
        inputFieldText: Editable?,
        registerInputFieldEnum: RegisterInputFieldEnum
    ) {
        inputFieldText?.let { value ->
            if (value.isNotBlank()) {
                dismissError(
                    registerInputFieldEnum
                )
            } else {
                setEmptyFieldError(
                    registerInputFieldEnum
                )
            }
        }
    }

    private fun dismissError(inputFieldToDismiss: RegisterInputFieldEnum) {
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