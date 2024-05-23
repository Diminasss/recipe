package com.suai.recepies

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

fun main() {
    val client = OkHttpClient()

    val formBody: RequestBody = FormBody.Builder()
        .add("login", "user_login")
        .add("password", "user_password")
        .add("nick_name", "user_nick_name")
        .add("date_of_birth", "user_date_of_birth")
        .build()

    val request: Request = Request.Builder()
        .url("http://127.0.0.1:5000/log_in")
        .post(formBody)
        .build()

    val response: Response = client.newCall(request).execute()

    if (response.isSuccessful) {
        val responseBody = response.body?.string()
        println("POST request successful: $responseBody")
    } else {
        println("POST request failed with code: ${response.code}")
    }
}


//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.main_activity)
//
//        // Пример использования функции для выполнения запроса
//        val query = "SELECT * FROM users"
//        val resultSet: ResultSet? = DatabaseHelper.executeQuery(query)
//
//        if (resultSet != null) {
//            while (resultSet.next()) {
//                val id = resultSet.getInt("id")
//                val login = resultSet.getString("login")
//                val password = resultSet.getString("password")
//                val nickName = resultSet.getString("nick_name")
//                val dateOfBirth = resultSet.getString("date_of_birth")
//
//                // Выводим информацию о пользователях
//                Toast.makeText(this, "User: $login, $nickName", Toast.LENGTH_LONG).show()
//            }
//        } else {
//            Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_LONG).show()
//        }
//    }
//}
//
//
//
