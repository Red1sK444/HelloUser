package com.r3d1r4ph.hellouser.presentation.common.extensions

import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout
import com.r3d1r4ph.hellouser.R

fun TextInputLayout.setErrorIfNotEmpty(@StringRes errorId: Int?) {
    error = resources.getString(errorId ?: R.string.empty)
    isErrorEnabled = errorId != null && errorId != R.string.empty_field
}