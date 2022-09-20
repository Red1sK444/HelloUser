package com.r3d1r4ph.domain.common.validation

import com.r3d1r4ph.domain.common.exceptions.*

sealed class ValidationRule(protected val input: String) {
    class IsNameOrSurname(input: String) : ValidationRule(input) {
        private companion object {
            const val MIN_LENGTH = 5
        }

        override fun apply(): Result<Unit> =
            when {
                input.length < MIN_LENGTH -> Result.failure(
                    TextLengthLessThanMinimalException(
                        MIN_LENGTH
                    )
                )
                !input.first().isUpperCase() -> Result.failure(FirstCharNoUpperCaseException())
                else -> Result.success(Unit)
            }
    }

    class IsBirthDate(input: String) : ValidationRule(input) {
        private companion object {
            const val DOT = '.'
            const val THREE = 3
        }

        override fun apply(): Result<Unit> =
            if (hasThreeComponents()) Result.success(Unit)
            else Result.failure(BirthDateException())

        private fun hasThreeComponents(): Boolean =
            input.split(DOT).size == THREE
    }

    class IsPassword(input: String) : ValidationRule(input) {
        private companion object {
            const val MIN_LENGTH = 8
        }

        override fun apply(): Result<Unit> =
            when {
                input.length < MIN_LENGTH -> Result.failure(
                    TextLengthLessThanMinimalException(
                        MIN_LENGTH
                    )
                )
                input.find { it.isDigit() } == null -> Result.failure(
                    NoDigitException()
                )
                input.find { it.isLowerCase() } == null -> Result.failure(
                    NoLowerCaseException()
                )
                input.find { it.isUpperCase() } == null -> Result.failure(
                    NoUpperCaseException()
                )
                else -> Result.success(Unit)
            }
    }

    class IsConfirmPassword(input: String, private val password: String) : ValidationRule(input) {

        override fun apply(): Result<Unit> =
            if (password != input) {
                Result.failure(PasswordNotEqualWithConfirmPasswordException())
            } else {
                IsPassword(input).apply()
            }

    }

    abstract fun apply(): Result<Unit>
}
