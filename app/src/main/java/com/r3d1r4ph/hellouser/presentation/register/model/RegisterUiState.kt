package com.r3d1r4ph.hellouser.presentation.register.model

import com.r3d1r4ph.hellouser.R
import com.r3d1r4ph.hellouser.presentation.common.exception.ExceptionHolder

data class RegisterUiState(
    val inputFieldErrors: Map<RegisterInputFieldEnum, ExceptionHolder?> = RegisterInputFieldEnum.values()
        .associateWith { ExceptionHolder(R.string.empty_field) }
) {
    val isRegisterAvailable: Boolean = inputFieldErrors.values.all { it == null }
}
