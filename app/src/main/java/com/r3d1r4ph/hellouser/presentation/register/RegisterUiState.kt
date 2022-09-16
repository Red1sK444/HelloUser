package com.r3d1r4ph.hellouser.presentation.register

import com.r3d1r4ph.hellouser.R

data class RegisterUiState(
    val inputFieldErrors: Map<RegisterInputFieldEnum, Int?> = RegisterInputFieldEnum.values()
        .associateWith { R.string.empty_field }
) {
    val isRegisterAvailable: Boolean = inputFieldErrors.values.all { it == null }
}
