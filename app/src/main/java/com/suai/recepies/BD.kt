package com.suai.recepies
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


//Добавление во внутреннюю БД
class BD(context: Context, factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, "app", factory, 1) {

    // Создание всей БД
    override fun onCreate(db: SQLiteDatabase?) { // Создание нужной для нас таблицы
        // Оприсание SQL-команды
        val query = "CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT, password TEXT, nick_name TEXT, date_of_birth TEXT, recipes_owner TEXT)"
        db!!.execSQL(query) // Передаем переменную, преобразуя в SQL команду
    }

    // Удаление и повторное создание БД (пересоздание БД)
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)

    }

    // Добавление нового пользователя
    fun addUser(login: String, password: String, nickname: String, dateOfBirth: String, recipesOwner: String) {
        val values = ContentValues()
        values.put("login", login)
        values.put("password", password)
        values.put("nick_name", nickname)
        values.put("date_of_birth", dateOfBirth)
        values.put("recipes_owner", recipesOwner)

        val db = this.writableDatabase
        db.insert("user", null, values)
        db.close()
    }
}
    // Функция, отвечающая за нахождение пользователя в Б}
//////Создание Post-запроса
////fun main() {
////    val url = "http://127.0.0.1:5000/log_in"
////    val json = "{login:Test}" // JSON тело запроса
////
////    val client = OkHttpClient()
////
////    val body = RequestBody.create(MediaType.parse("application/json"), json)
////    val request = Request.Builder()
////        .url(url)
////        .post(body)
////        .build()
////
////    client.newCall(request).enqueue(object : Callback {
////        override fun onFailure(call: Call, e: IOException) {
////            e.printStackTrace()
////        }
////
////        override fun onResponse(call: Call, response: Response) {
////            println(response.body()?.string())
////        }
////    })
////}
//
