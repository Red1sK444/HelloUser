package com.r3d1r4ph.domain.common.validation

import com.r3d1r4ph.domain.common.exceptions.*

sealed class ValidationRule(protected val input: String) {
    class IsEmail(input: String) : ValidationRule(input) {
        private companion object {
            const val AT_SIGN = '@'
        }

        override fun apply(): Result<Unit> =
            when {
                input.contains(AT_SIGN) -> Result.success(Unit)
                else -> Result.failure(NoAtSignException())
            }
    }

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
                input.first().isUpperCase() -> Result.failure(FirstCharNoUpperCaseException())
                else -> Result.success(Unit)
            }
    }

    class IsBirthDate(input: String) : ValidationRule(input) {

        override fun apply(): Result<Unit> =
            Result.success(Unit)
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
                !input.contains(Regex("[0-9]")) -> Result.failure(
                    NoDigitException()
                )
                !input.contains(Regex("[a-z]")) -> Result.failure(
                    NoLowerCaseException()
                )
                !input.contains(Regex("[A-Z]")) -> Result.failure(
                    NoUpperCaseException()
                )
                else -> Result.success(Unit)
            }
    }

    abstract fun apply(): Result<Unit>
}
