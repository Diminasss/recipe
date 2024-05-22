package com.suai.recepies
//import android.content.ContentValues
//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//
////Добавление во внутреннюю БД
//class BD(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
//    SQLiteOpenHelper(context, "app", factory, 1) {
//
//    // Создание всей БД
//    override fun onCreate(db: SQLiteDatabase?) { // Создание нужной для нас таблицы
//        // Оприсание SQL-команды
//        val query = "CREATE TABLE users (id INT PRIMARY KEY, login TEXT, password TEXT, nick_name TEXT, date_of_birth TEXT)"
//        db!!.execSQL(query) // Передаем переменную, преобразуя в SQL команду
//    }
//
//    // Удаление и повторное создание БД (пересоздание БД)
//    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
//        db!!.execSQL("DROP TABLE IF EXISTS users")
//        onCreate(db)
//
//    }
//    // Добавление нового пользователя
//    fun addUser(user: User){
//        val values = ContentValues()
//        values.put("login", user.login)
//        values.put("password", user.password)
//        values.put("nick_name", user.nick_name)
//        values.put("date_of_birth", user.date_of_birth)
//
//        val db = this.writableDatabase
//        db.insert("user", null, values)
//        db.close()
//    }
//
//    // Функция, отвечающая за нахождение пользователя в БД
//    fun getUser(login:String, password: String): Boolean {
//        val db = this.readableDatabase
//        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND password = '$password'", null)
//
//        return result.moveToFirst()
//    }
//
//}
////Создание Post-запроса
//fun main() {
//    val url = "http://127.0.0.1:5000/log_in"
//    val json = "{login:Test}" // JSON тело запроса
//
//    val client = OkHttpClient()
//
//    val body = RequestBody.create(MediaType.parse("application/json"), json)
//    val request = Request.Builder()
//        .url(url)
//        .post(body)
//        .build()
//
//    client.newCall(request).enqueue(object : Callback {
//        override fun onFailure(call: Call, e: IOException) {
//            e.printStackTrace()
//        }
//
//        override fun onResponse(call: Call, response: Response) {
//            println(response.body()?.string())
//        }
//    })
//}

