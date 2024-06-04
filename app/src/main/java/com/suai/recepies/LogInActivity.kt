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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //Проверка пользователя в базе данных
        setContentView(R.layout.main_activity)

        val linkToReg: TextView = findViewById(R.id.вРегистрация)
        linkToReg.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
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
                        val intent = Intent(this, MenuActivity::class.java)



                            // Запись пользователя в базу данных

                        startActivity(intent)
                    }
                    else if (result["result"] == "invalid_password"){
                        Toast.makeText(this, "Неверный пароль", Toast.LENGTH_LONG).show()
                    }
                    else if (result["result"] == "no_user"){
                        Toast.makeText(this, "Такого пользователя не существует", Toast.LENGTH_LONG).show()
                    }
                }
                Toast.makeText(this, "Вход выполнен успешно", Toast.LENGTH_LONG).show()
                println(result)
            }
        }
    }
}