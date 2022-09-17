package com.r3d1r4ph.hellouser.presentation.common.extensions

import android.view.View
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.Group
import com.google.android.material.textfield.TextInputLayout
import com.r3d1r4ph.hellouser.R

fun TextInputLayout.setErrorIfNotEmpty(@StringRes errorId: Int?) {
    error = resources.getString(errorId ?: R.string.empty)
    isErrorEnabled = errorId != null && errorId != R.string.empty_field
}

inline fun <reified T : View> Group.forEachView(
    operation: (T) -> Unit
) {
    referencedIds
        .map { id -> rootView.findViewById(id) as? T }
        .filterNotNull()
        .forEach(operation)
}