package com.estiven.datossinmvvm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<User>


    // Métodos para eliminar o actualizar usuarios (opcional)
     @Delete
     suspend fun deleteUser(user: User)

     @Update
     suspend fun updateUser(user: User)
}
