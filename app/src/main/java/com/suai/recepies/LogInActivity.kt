package com.suai.recepies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.CardDefaults.shape
import androidx.compose.material3.CardElevation
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


class LogInActivity : AppCompatActivity() {
    private val databaseManager = DatabaseManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        //println("Включение онкреэт")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        if (databaseManager.userIsInTable()) {
            //println("Пользователь уже в таблице")

            val dictionaryFromDB = databaseManager.getUserLoginAndPasswordFromDB()

            //println(dictionaryFromDB)
            val logInHTTP = "http://10.0.2.2:5000/log_in"
            val result = doPost(dictionaryFromDB, logInHTTP)
            //println("Успешный post запрос")
            if (result != null) {
                if (result["result"] == "successfully") {
                    //println("Вход выполнен успешно")
                    Toast.makeText(this, "Вход выполнен успешно", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MenuActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                } else if (result["result"] == "invalid_password") {
                    //println("Неверный пароль")
                    Toast.makeText(this, "Неверный пароль", Toast.LENGTH_LONG).show()
                    databaseManager.onUpgradeUser()
                    listenButton()

                } else if (result["result"] == "no_user") {
                    //println("Такого пользователя нет")
                    Toast.makeText(this, "Такого пользователя больше нет", Toast.LENGTH_LONG).show()
                    databaseManager.onUpgradeUser()
                    listenButton()

                } else{
                    //println("Неизвестная ошибка")
                    Toast.makeText(this, "Неизвестная ошибка", Toast.LENGTH_LONG).show()
                    listenButton()

                }
            } else{
                Toast.makeText(this, "Не удалось связаться с базой данных", Toast.LENGTH_LONG).show()
                listenButton()
            }

            //println("Завершение блока пользователь уже в базе")
            //println(result)

        } else {
            //println("Пользователя нет в базе на телефоне")
            listenButton()
        }
    }

    private fun listenButton(){
        val linkToReg: TextView = findViewById(R.id.вРегистрация)
        linkToReg.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        val userLogin: EditText = findViewById(R.id.login_signup)
        val userPassword: EditText = findViewById(R.id.password_signup)
        val button: Button = findViewById(R.id.signin)


        button.setOnClickListener {
            //println("Кнопка сработала!")
            val login = userLogin.text.toString().trim()
            val password = userPassword.text.toString().trim()

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            } else if (password.length < 4) {
                Toast.makeText(this, "Вы ввели пароль меньше 4 символов", Toast.LENGTH_LONG)
                    .show()
            } else {
                val dictionary: Map<String, String> =
                    mapOf("login" to login, "password" to password)
                val logInHTTP = "http://10.0.2.2:5000/log_in"
                val result = doPost(dictionary, logInHTTP)
                if (result != null) {
                    if (result["result"] == "successfully") {
                        Toast.makeText(this, "Вход выполнен успешно", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MenuActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


                        databaseManager.insertUserToDB(
                            login,
                            password,
                            result["nick_name"].toString(),
                            result["date_of_birth"].toString()
                        )

                        startActivity(intent)
                    } else if (result["result"] == "invalid_password") {
                        Toast.makeText(this, "Неверный пароль", Toast.LENGTH_LONG).show()
                    } else if (result["result"] == "no_user") {
                        Toast.makeText(
                            this,
                            "Такого пользователя не существует",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else{
                    Toast.makeText(this, "Не удалось связаться с базой данных", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun showLoginPage(){
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.Unspecified),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = cardElevation(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.log_in_person),
                contentDescription = "Анонимная аватарка",
                modifier = Modifier.size(145.dp).fillMaxSize()
                )
        }
        Text(text="Вход")
        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = { Text("Введите логин") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Введите логин") }
        )
        Button(
            onClick = {
            // Действие при нажатии на кнопку
        },
            ) {
            Text("Войти")
        }

        Text(
            text = "Нет аккаунта? \nЗарегистрироваться!",
            textAlign = TextAlign.Center,
            color = Color.Blue, // Цвет текста
            modifier = Modifier
                .clickable {
                    // Действие при нажатии на текст

                }
                .padding(16.dp)
        )

    }
}

