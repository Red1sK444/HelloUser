package com.r3d1r4ph.hellouser.presentation.common.extensions

import android.view.View
import androidx.constraintlayout.widget.Group
import com.google.android.material.textfield.TextInputLayout
import com.r3d1r4ph.hellouser.R
import com.r3d1r4ph.hellouser.presentation.common.exception.ExceptionHolder

fun TextInputLayout.setErrorIfNotEmpty(exceptionHolder: ExceptionHolder?) {
    exceptionHolder?.let { holder ->
        error = resources.getString(holder.messageId, holder.formatArg)
    }

    isErrorEnabled = exceptionHolder != null && exceptionHolder.messageId != R.string.empty_field
}

inline fun <reified T : View> Group.forEachView(
    operation: (T) -> Unit
) {
    referencedIds
        .map { id -> rootView.findViewById(id) as? T }
        .filterNotNull()
        .forEach(operation)
}