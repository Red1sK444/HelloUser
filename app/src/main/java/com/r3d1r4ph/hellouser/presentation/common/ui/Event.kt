package com.r3d1r4ph.hellouser.presentation.common.ui

class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    @Synchronized
    fun getContentIfNotHandled(): T? =
        if (hasBeenHandled)  {
            null
        } else {
            hasBeenHandled = true
            content
        }
}