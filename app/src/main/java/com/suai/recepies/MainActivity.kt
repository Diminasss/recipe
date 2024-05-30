package com.suai.recepies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_activity)

        val linkToReg: TextView = findViewById(R.id.вРегистрация)
        linkToReg.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        val userLogin: EditText = findViewById(R.id.login_signup)
        val userPassword: EditText = findViewById(R.id.password_signup)
        val button: Button = findViewById(R.id.signin)


        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val password = userPassword.text.toString().trim()

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            }
            else if (password.length < 4) {
                Toast.makeText(this, "Вы ввели пароль меньше 4 символов", Toast.LENGTH_LONG).show()
            }
            else{
                val dictionary: Map<String, String> = mapOf("login" to login, "password" to password)
                val logInHTTP = "http://10.0.2.2:5000/log_in"
                val result = doPost(dictionary, logInHTTP)
                if (result != null){
                    if (result["result"] == "successfully"){
                        Toast.makeText(this, "Вход выполнен успешно", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, Items::class.java)
                        startActivity(intent)
                    }
                    else if (result["result"] == "invalid_password"){
                        Toast.makeText(this, "Вход выполнен успешно", Toast.LENGTH_LONG).show()
                    }
                    else if (result["result"] == "no_user"){
                        Toast.makeText(this, "Вход выполнен успешно", Toast.LENGTH_LONG).show()
                    }
                }
                Toast.makeText(this, "Вход выполнен успешно", Toast.LENGTH_LONG).show()
                println(result)
            }

        //else {
                // Взятие из БД созданного объекта
//                val db = BD(this, null)
//                val isAuth = db.getUser(login, password)

//                if (isAuth) {
//                    Toast.makeText(this, "Пользователь $login авторизован", Toast.LENGTH_LONG)
//                        .show()
//                    // Очищение полей
//                    user_login.text.clear()
//                    user_password.text.clear()
////                    val intent = Intent(this, Items::class.java)
////                    startActivity(intent)
//                } else
//                    Toast.makeText(this, "Пользователь $login НЕ авторизован", Toast.LENGTH_LONG)
//                        .show()
//
//            }
        }
    }
}

//        //Привязываем обработчик нажатия кнопки "Вход"
//        loginButton.setOnClickListener {
//            // Создаем Intent для перехода на другую активити
//            val intent = Intent(this, SignUpActivity::class.java)
//            startActivity(intent)
//        }

