package com.r3d1r4ph.hellouser.presentation.register

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.r3d1r4ph.hellouser.R
import com.r3d1r4ph.hellouser.presentation.register.RegisterInputFieldEnum.*

enum class RegisterInputFieldEnum {
    NAME,
    SURNAME,
    BIRTH_DATE,
    PASSWORD,
    CONFIRM_PASSWORD;

    companion object {
        fun fromTextInputLayoutId(@IdRes textInputLayoutId: Int): RegisterInputFieldEnum =
            when (textInputLayoutId) {
                R.id.registerNameTextInputLayout -> NAME
                R.id.registerSurnameTextInputLayout -> SURNAME
                R.id.registerBirthDateTextInputLayout -> BIRTH_DATE
                R.id.registerPasswordTextInputLayout -> PASSWORD
                R.id.registerConfirmPasswordTextInputLayout -> CONFIRM_PASSWORD
                else -> throw Exception("Invalid text input layout Id - $textInputLayoutId")
            }

        fun fromTextInputEditTextId(@IdRes textInputEditTextId: Int): RegisterInputFieldEnum =
            when (textInputEditTextId) {
                R.id.registerNameTextInputEditText -> NAME
                R.id.registerSurnameTextInputEditText -> SURNAME
                R.id.registerBirthDateTextInputEditText -> BIRTH_DATE
                R.id.registerPasswordTextInputEditText -> PASSWORD
                R.id.registerConfirmPasswordTextInputEditText -> CONFIRM_PASSWORD
                else -> throw Exception("Invalid text input edit text Id - $textInputEditTextId")
            }
    }
}

@StringRes
fun RegisterInputFieldEnum.getTextInputLayoutId(): Int = when (this) {
    NAME -> R.id.registerNameTextInputLayout
    SURNAME -> R.id.registerSurnameTextInputLayout
    BIRTH_DATE -> R.id.registerBirthDateTextInputLayout
    PASSWORD -> R.id.registerPasswordTextInputLayout
    CONFIRM_PASSWORD -> R.id.registerConfirmPasswordTextInputLayout
}

@StringRes
fun RegisterInputFieldEnum.getTextInputEditTextId(): Int = when (this) {
    NAME -> R.id.registerNameTextInputEditText
    SURNAME -> R.id.registerSurnameTextInputEditText
    BIRTH_DATE -> R.id.registerBirthDateTextInputEditText
    PASSWORD -> R.id.registerPasswordTextInputEditText
    CONFIRM_PASSWORD -> R.id.registerConfirmPasswordTextInputEditText
}