package com.r3d1r4ph.hellouser.presentation.register

import androidx.annotation.StringRes

data class RegisterUiState(
    val inputFieldErrors: Map<RegisterInputFieldEnum, Int?> = RegisterInputFieldEnum.values()
        .associateWith { null },
    val notEmpty: Boolean = false
)
