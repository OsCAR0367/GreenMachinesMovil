package com.estiven.datossinmvvm

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun ScreenUser() {
    val context = LocalContext.current
    var db: UserDatabase
    var id by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dataUser = remember { mutableStateOf("") }

    db = crearDatabase(context)
    val dao = db.userDao()

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(50.dp))
        TextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("ID (solo lectura)") },
            readOnly = true,
            singleLine = true
        )
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            singleLine = true
        )
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            singleLine = true
        )
        Button(
            onClick = {
                val user = User(
                    username = id,
                    password = "default_password",
                    firstName = firstName,
                    lastName = lastName
                )
                coroutineScope.launch {
                    AgregarUsuario(user, dao)
                }
                firstName = ""
                lastName = ""
                id = "" // Restablecer el ID
            }
        ) {
            Text("Agregar Usuario", fontSize = 16.sp)
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    val data = getUsers(dao)
                    dataUser.value = data
                }
            }
        ) {
            Text("Listar Usuarios", fontSize = 16.sp)
        }
        Text(text = dataUser.value, fontSize = 20.sp)
    }
}

@Composable
fun crearDatabase(context: Context): UserDatabase {
    return Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        "user_db"
    ).build()
}

suspend fun getUsers(dao: UserDao): String {
    var rpta = ""
    val users = dao.getAll()
    users.forEach { user ->
        val fila = "${user.firstName} - ${user.lastName}\n"
        rpta += fila
    }
    return rpta
}

suspend fun AgregarUsuario(user: User, dao: UserDao) {
    try {
        dao.insertUser(user)
    } catch (e: Exception) {
        android.util.Log.e("User", "Error insertando: ${e.message}")
    }
}
