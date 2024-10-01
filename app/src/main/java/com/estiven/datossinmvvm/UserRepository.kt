package com.estiven.datossinmvvm

class UserRepository(private val userDao: UserDao) {

    // Método para insertar un usuario
    suspend fun insert(user: User) {
        userDao.insertUser(user)
    }

    // Método para obtener un usuario por nombre
    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    // Puedes añadir más métodos según sea necesario
}