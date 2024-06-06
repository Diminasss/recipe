package com.suai.recepies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class LogInActivity : AppCompatActivity() {
    private val databaseManager = DatabaseManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        println("Включение онкреэт")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        databaseManager.openDB()
        if (databaseManager.userIsInTable()) {
            println("Пользователь уже в таблице")

            val dictionaryFromDB = databaseManager.getUserLoginAndPasswordFromDB()

            println(dictionaryFromDB)
            val logInHTTP = "http://10.0.2.2:5000/log_in"
            val result = doPost(dictionaryFromDB, logInHTTP)
            println("Успешный post запрос")
            if (result != null) {
                if (result["result"] == "successfully") {
                    println("Вход выполнен успешно")
                    Toast.makeText(this, "Вход выполнен успешно", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)

                } else if (result["result"] == "invalid_password") {
                    println("Неверный пароль")
                    Toast.makeText(this, "Неверный пароль", Toast.LENGTH_LONG).show()
                    databaseManager.onUpgrade()
                    listenButton()

                } else if (result["result"] == "no_user") {
                    println("Такого пользователя нет")
                    Toast.makeText(this, "Такого пользователя больше нет", Toast.LENGTH_LONG).show()
                    databaseManager.onUpgrade()
                    listenButton()

                } else{
                    println("Неизвестная ошибка")
                    Toast.makeText(this, "Неизвестная ошибка", Toast.LENGTH_LONG).show()
                    listenButton()

                }
            } else{
                Toast.makeText(this, "Не удалось связаться с базой данных", Toast.LENGTH_LONG).show()
                listenButton()
            }

            println("Завершение блока пользователь уже в базе")
            println(result)

        } else {
            println("Пользователя нет в базе на телефоне")
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
            println("Кнопка сработала!")
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

                        databaseManager.openDB()
                        databaseManager.insertUserToDB(
                            login,
                            password,
                            result["nick_name"].toString(),
                            result["date_of_birth"].toString(),
                            result["recipes_owner"].toString()
                        )
                        databaseManager.closeDB()



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