package com.suai.recepies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
//
//        var sp = getSharedPreferences("PS", Context.MODE_PRIVATE) // Shered Preference
//        //if (sp.getString("TY", "-9") != "-9")

        val linkToAuth: TextView = findViewById(R.id.вВойти)
        linkToAuth.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)}

        val user_login: EditText = findViewById(R.id.login_signup)
        val user_password: EditText = findViewById(R.id.password_signup)
        val user_nick_name: EditText = findViewById(R.id.nick_name)
        val user_date_of_birth: EditText = findViewById(R.id.date_of_birth)
        val button: Button = findViewById(R.id.signup)

        button.setOnClickListener {
            val login = user_login.text.toString().trim()
            val password = user_password.text.toString().trim()
            val nick_name = user_nick_name.text.toString().trim()
            val date_of_birth = user_date_of_birth.text.toString().trim()

            if (login == "" || password == "" || nick_name == "" || date_of_birth == "") {  //login.contains('@')
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            }

            else {

                val user = User(login, password, nick_name, date_of_birth)
                // Создание нового объекта (система регистрации)
                val db = BD(this, null)
                db.addUser(user)
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