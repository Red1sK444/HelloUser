package com.r3d1r4ph.hellouser.presentation.register

import androidx.annotation.IdRes
import com.r3d1r4ph.hellouser.R

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