package com.r3d1r4ph.hellouser.presentation.common.exception

import androidx.annotation.StringRes

data class ExceptionHolder(
    @StringRes val messageId: Int,
    val formatArg: String? = null
)
