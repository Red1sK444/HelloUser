package com.r3d1r4ph.data.user.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.r3d1r4ph.domain.entities.UserEntity

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val surname: String,
    val birthDate: String,
    val password: String
) {
    companion object {
        fun fromDomain(domain: UserEntity): User =
            User(
                name = domain.name,
                surname = domain.surname,
                birthDate = domain.birthDate,
                password = domain.password
            )
    }

    fun toDomain(): UserEntity =
        UserEntity(
            name = name,
            surname = surname,
            birthDate = birthDate,
            password = password
        )
}