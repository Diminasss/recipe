package com.suai.recepies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_activity)


        val user_login: EditText = findViewById(R.id.login_signup)
        val user_password: EditText = findViewById(R.id.password_signup)
        val button: Button = findViewById(R.id.signin)

        val linkToReg: TextView = findViewById(R.id.вРегистрация)

        linkToReg.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val login = user_login.text.toString().trim()
            val password = user_password.text.toString().trim()

            if (login == "" || password.isEmpty()) {  //login.contains('@')
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            } else if (password.length < 4) {
                Toast.makeText(this, "Вы ввели пароль меньше 4 символов", Toast.LENGTH_LONG).show()
            } else {
                // Взятие из БД созданного объекта
                val db = BD(this, null)
                val isAuth = db.getUser(login, password)

                if (isAuth) {
                    Toast.makeText(this, "Пользователь $login авторизован", Toast.LENGTH_LONG)
                        .show()
                    // Очищение полей
                    user_login.text.clear()
                    user_password.text.clear()
                    val intent = Intent(this, Items::class.java)
                    startActivity(intent)

                } else
                    Toast.makeText(this, "Пользователь $login НЕ авторизован", Toast.LENGTH_LONG)
                        .show()
            }
        }
    }
}
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        // Привязываем обработчик нажатия кнопки "Вход"
//        loginButton.setOnClickListener {
//            // Создаем Intent для перехода на другую активити
//            val intent = Intent(this, SignUpActivity::class.java)
//            startActivity(intent)
//        }
