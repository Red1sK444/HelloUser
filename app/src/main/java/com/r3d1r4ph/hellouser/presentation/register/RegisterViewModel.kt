package com.r3d1r4ph.hellouser.presentation.register

import androidx.lifecycle.*
import com.r3d1r4ph.hellouser.presentation.common.ui.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel : ViewModel() {

    private val _uiState = MutableLiveData(RegisterUiState())
    val uiState: LiveData<RegisterUiState>
        get() = _uiState.map { it }

    private val _uiAction = MutableLiveData<Event<RegisterAction>>()
    val uiAction: LiveData<Event<RegisterAction>>
        get() = _uiAction.map { it }

    fun dismissError(inputFieldToDismiss: RegisterInputFieldEnum) {
        viewModelScope.launch {
            _uiState.value?.let {
                _uiState.value =
                    it.copy(
                        inputFieldErrors = it.inputFieldErrors.keys.associateWith { inputFieldEnum ->
                            if (inputFieldEnum == inputFieldToDismiss) null else it.inputFieldErrors[inputFieldEnum]
                        }
                    )
            }
        }
    }
}