package com.suai.recepies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_activity)

        var user_login: EditText = findViewById(R.id.login)
        var user_password: EditText = findViewById(R.id.password)
        var button: Button = findViewById(R.id.signin)





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Привязываем обработчик нажатия кнопки "Вход"
//        loginButton.setOnClickListener {
//            // Создаем Intent для перехода на другую активити
//            val intent = Intent(this, SignUpActivity::class.java)
//            startActivity(intent)
//        }


    }
}