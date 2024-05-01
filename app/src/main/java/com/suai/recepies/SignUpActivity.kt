package com.suai.recepies

import android.content.Context
import android.os.Bundle
import android.widget.Button
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
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        var sp = getSharedPreferences("PS", Context.MODE_PRIVATE) // Shered Preference
        //if (sp.getString("TY", "-9") != "-9")

        var user_login:TextView = findViewById(R.id.login)
        var user_password:TextView = findViewById(R.id.password)
        var user_nick_name:TextView = findViewById(R.id.nick_name)
        var button:Button = findViewById(R.id.signup)

        button.setOnClickListener{
            val login  = user_login.text.trim()
            val password  = user_password.text.trim()
            val nick_name  = user_nick_name.text.trim()

            if (login.isEmpty() || password.isEmpty() || nick_name.isEmpty()) {  //login.contains('@')
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            } else if (password.length < 4) {
                Toast.makeText(this, "Вы ввели пароль меньше 4 символов", Toast.LENGTH_LONG).show()
            }
            else{ // регистрация пользователя

            }

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}