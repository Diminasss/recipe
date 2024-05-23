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
            startActivity(intent)
        }

        val userLogin: EditText = findViewById(R.id.login_signup)
        val userPassword: EditText = findViewById(R.id.password_signup)
        val userNickName: EditText = findViewById(R.id.nick_name)
        val userDateOfBirth: EditText = findViewById(R.id.date_of_birth)
        val button: Button = findViewById(R.id.signup)

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val password = userPassword.text.toString().trim()
            val nickName = userNickName.text.toString().trim()
            val dateOfBirth = userDateOfBirth.text.toString().trim()


            if (login.isEmpty() || password.isEmpty() || nickName.isEmpty() || dateOfBirth.isEmpty()) {

                Toast.makeText(this, "Все поля данной формы обязательны для заполнения", Toast.LENGTH_LONG).show()
            }
            else {
                val registerHTTP: String = "http://10.0.2.2:5000/register"
                val dictionary: Map<String, String> = mapOf("login" to login, "password" to password, "nick_name" to nickName, "date_of_birth" to dateOfBirth)
                val result = doPost(dictionary, registerHTTP)
                if (result != null){
                    if (result["result"] == "successfully") {
                        Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else if(result["result"] == "user_is_already_registered"){
                        Toast.makeText(this, "Пользователь $login уже существует", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else if(result["result"] == "data_base_error"){
                        Toast.makeText(this, "Ошибка базы данных", Toast.LENGTH_LONG).show()
                    }
                    else if(result["result"] == "login_password_and_nick_name_are_necessary"){
                        Toast.makeText(this, "Все поля данной формы обязательны для заполнения", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }


                // Очищение полей
                userLogin.text.clear()
                userPassword.text.clear()
                userNickName.text.clear()
                userDateOfBirth.text.clear()
                    //                val user = User(login, password, nickName, dateOfBirth)
//                // Создание нового объекта (система регистрации)
//              val db = BD(this, null)
//              db.addUser(user)
            }
        }
    }
}