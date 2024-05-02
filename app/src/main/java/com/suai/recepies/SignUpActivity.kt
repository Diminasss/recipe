package com.suai.recepies

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
//
//        var sp = getSharedPreferences("PS", Context.MODE_PRIVATE) // Shered Preference
//        //if (sp.getString("TY", "-9") != "-9")

        var user_login: EditText = findViewById(R.id.login)
        var user_password: EditText = findViewById(R.id.password)
        var user_nick_name: EditText = findViewById(R.id.nick_name)
        var user_date_of_birth: EditText = findViewById(R.id.date_of_birth)
        var button: Button = findViewById(R.id.signup)

        button.setOnClickListener {
            val login = user_login.text.toString().trim()
            val password = user_password.text.toString().trim()
            val nick_name = user_nick_name.text.toString().trim()
            val date_of_birth = user_date_of_birth.toString().trim()

            if (login == "" || password.isEmpty() || nick_name.isEmpty() || date_of_birth.isEmpty()) {  //login.contains('@')
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            } else if (password.length < 4) {
                Toast.makeText(this, "Вы ввели пароль меньше 4 символов", Toast.LENGTH_LONG).show()
            } else if (date_of_birth.contains('.') && (date_of_birth.length != 10)){
                Toast.makeText(this, "Формат ввода не соответствует дате. Введите ДД.ММ.ГГГГ", Toast.LENGTH_LONG).show()// регистрация пользователя
            } else {
                val user = User(login, password, nick_name, date_of_birth)

                val db = BD(this, null)
                db.addUsers(user)
                Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()

                // Очищение полей
                user_login.text.clear()
                user_password.text.clear()
                user_nick_name.text.clear()
                user_date_of_birth.text.clear()


            }
        }
    }
}