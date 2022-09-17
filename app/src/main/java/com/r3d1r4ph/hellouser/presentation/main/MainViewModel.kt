package com.r3d1r4ph.hellouser.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.r3d1r4ph.hellouser.presentation.common.ui.Event

class MainViewModel : ViewModel() {

    private val _uiAction = MutableLiveData<Event<MainAction>>()
    val uiAction: LiveData<Event<MainAction>>
        get() = _uiAction.map { it }

    fun onHelloClick() {
        _uiAction.value = Event(MainAction.ShowGreetingDialog)
    }
}