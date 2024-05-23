package com.suai.recepies

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking



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

                // Создаем корутину в блоке runBlocking
                val result = runBlocking {
                    // Запускаем корутину с помощью launch и передаем контекст Dispatchers.IO,
                    // чтобы выполнить операцию ввода-вывода (в вашем случае - сетевой запрос) в фоновом потоке
                    val deferredResult = async(Dispatchers.IO) {
                        // Вызываем функцию register() в контексте корутины
                        register(login=login, password=password, nickname=nickName, dateOfBirth=dateOfBirth)

                    }
                    deferredResult.await()
                }
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