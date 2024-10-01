package com.estiven.datossinmvvm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val username: String,
    val password: String,
    val firstName: String,
    val lastName: String
)

