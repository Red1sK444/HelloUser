package com.r3d1r4ph.hellouser.presentation.common.dialog

import androidx.lifecycle.*
import com.r3d1r4ph.domain.usecases.GetUserUseCase
import com.r3d1r4ph.hellouser.presentation.common.dialog.model.GreetingAction
import com.r3d1r4ph.hellouser.presentation.common.dialog.model.GreetingUiState
import com.r3d1r4ph.hellouser.presentation.common.ui.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GreetingViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(GreetingUiState())
    val uiState: LiveData<GreetingUiState>
        get() = _uiState.map { it }

    private val _uiAction = MutableLiveData<Event<GreetingAction>>()
    val uiAction: LiveData<Event<GreetingAction>>
        get() = _uiAction.map { it }

    init {
        setUserName()
    }

    private fun setUserName() {
        viewModelScope.launch {
            getUserUseCase.execute()
                .onSuccess { userEntity ->
                    _uiState.value = GreetingUiState(userName = userEntity.name)
                }
                .onFailure {
                    onContinueClick()
                }
        }
    }

    fun onContinueClick() {
        _uiAction.value = Event(GreetingAction.DismissDialog)
    }
}