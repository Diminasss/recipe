package com.suai.recepies

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.regex.Pattern


class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val linkToAuth: TextView = findViewById(R.id.вВойти)
        linkToAuth.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        val userLogin: EditText = findViewById(R.id.login_signup)
        val userPassword: EditText = findViewById(R.id.password_signup)
        val userNickName: EditText = findViewById(R.id.nick_name)
        val userDateOfBirth: EditText = findViewById(R.id.date_of_birth)
        val button: Button = findViewById(R.id.signup)


        userDateOfBirth.setOnClickListener {
            showDatePicker(userDateOfBirth)
        }

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val password = userPassword.text.toString().trim()
            val nickName = userNickName.text.toString().trim()
            val dateOfBirth = userDateOfBirth.text.toString().trim()


            if (login.isEmpty() || password.isEmpty() || nickName.isEmpty() || dateOfBirth.isEmpty()) {
                Toast.makeText(
                    this,
                    "Все поля данной формы обязательны для заполнения",
                    Toast.LENGTH_LONG
                ).show()
            }
//            else if (!isValidDate(dateOfBirth)) {
//                Toast.makeText(this, "Неверный формат даты", Toast.LENGTH_LONG).show()
//            }
            else {
                if (validateDate(dateOfBirth)) {
                    val registerHTTP: String = "http://10.0.2.2:5000/register"
                    val dictionary: Map<String, String> = mapOf(
                        "login" to login,
                        "password" to password,
                        "nick_name" to nickName,
                        "date_of_birth" to dateOfBirth
                    )
                    val result = doPost(dictionary, registerHTTP)
                    if (result != null) {
                        if (result["result"] == "successfully") {
                            Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG)
                                .show()
                            //validateDate(result["result"].toString())
                            val intent = Intent(this, LogInActivity::class.java)
                            startActivity(intent)
                        } else if (result["result"] == "user_is_already_registered") {
                            Toast.makeText(
                                this,
                                "Пользователь $login уже существует",
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(this, LogInActivity::class.java)
                            startActivity(intent)
                        } else if (result["result"] == "data_base_error") {
                            Toast.makeText(this, "Ошибка базы данных", Toast.LENGTH_LONG).show()
                        } else if (result["result"] == "login_password_and_nick_name_are_necessary") {
                            Toast.makeText(
                                this,
                                "Все поля данной формы обязательны для заполнения",
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(this, LogInActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    // Очищение полей
                    userLogin.text.clear()
                    userPassword.text.clear()
                    userNickName.text.clear()
                    userDateOfBirth.text.clear()
                } else {
                    Toast.makeText(this, "Неверный формат даты. Ввеите ДД.ММ.ГГГГ", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun validateDate(date: String): Boolean {
        // Регулярное выражение для формата ДД.ММ.ГГГГ
        val datePattern = "^([0-2][0-9]|(3)[0-1])\\.(0[1-9]|1[0-2])\\.([0-9]{4})$"
        val pattern = Pattern.compile(datePattern)
        val matcher = pattern.matcher(date)
        return matcher.matches()
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDay = if (selectedDay < 10) "0$selectedDay" else "$selectedDay"
                val formattedMonth = if (selectedMonth + 1 < 10) "0${selectedMonth + 1}" else "${selectedMonth + 1}"
                editText.setText("$formattedDay.$formattedMonth.$selectedYear")
            },
            day, month, year
        )
        datePickerDialog.show()
    }
}


