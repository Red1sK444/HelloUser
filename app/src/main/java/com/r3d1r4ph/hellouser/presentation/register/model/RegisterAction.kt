package com.r3d1r4ph.hellouser.presentation.register.model

import androidx.annotation.StringRes

sealed class RegisterAction {
    object OpenMainScreen : RegisterAction()
    data class Error(@StringRes val messageId: Int) : RegisterAction()
}
