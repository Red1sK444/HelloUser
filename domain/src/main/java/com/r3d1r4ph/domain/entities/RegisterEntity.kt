package com.r3d1r4ph.domain.entities

data class RegisterEntity(
    val name: String,
    val surname: String,
    val birthDate: String,
    val password: String,
    val confirmPassword: String
) {
    fun toUserEntity(): UserEntity =
        UserEntity(
            name = name,
            surname = surname,
            birthDate = birthDate,
            password = password
        )
}
