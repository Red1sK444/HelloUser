package com.r3d1r4ph.domain.common.exceptions

class NoAtSignException : Exception()

class TextLengthLessThanMinimalException(length: Int) : Exception(length.toString())

class FirstCharNoUpperCaseException : Exception()

class NoUpperCaseException : Exception()

class NoLowerCaseException : Exception()

class NoDigitException : Exception()

class PasswordNotEqualWithConfirmPasswordException : Exception()

class RegisterUserException : Exception()

class NoRegisteredUserException : Exception()