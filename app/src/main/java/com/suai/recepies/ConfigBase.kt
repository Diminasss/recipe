package com.suai.recepies

import androidx.annotation.WorkerThread
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

const val serverHTTP: String = "http://10.0.2.2:5000/test"

@WorkerThread
suspend fun register(login: String, password: String, nickname: String, dateOfBirth: String) {

    // Создание данных и сериализация
    val dictionary: Map<String, String> = mapOf("login" to login, "password" to password, "nick_name" to nickname, "date_of_birth" to dateOfBirth)
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val adapter = moshi.adapter(Map::class.java)
    println("Создан словарь, моши и адаптор $dictionary")


    // Получение полноценного JSON и метаданных к нему
    val jsonRequest = adapter.toJson(dictionary)
    val JSON = "application/json; charset=utf-8".toMediaType()
    println("Словарь переведён в JSON $jsonRequest")
    println("Созданы метаданные JSON $JSON")


    // Создание клиента и формирование запроса
    val client = OkHttpClient()
    println("Создан клиент")
    val body: RequestBody = jsonRequest.toRequestBody(JSON)
    println("Созданл тело запроса")
    val request = Request.Builder().url(serverHTTP).post(body).build()
    println("Создан запрос")


    // Попытка проведения Post запроса
    try{
        println("Выполнение запроса")
        client.newCall(request).execute().use {response ->
            if (!response.isSuccessful){

                throw IOException("Запрос к серверу не был успешным")
            }
            println("Запрос прошёл успешно")
            val responseAdapter = moshi.adapter<Map<String, Any>>(Map::class.java)
            val registerResponse = responseAdapter.fromJson(response.body!!.string())
            println("Ответ успешно преобразован в словарь Kotlin $registerResponse")
        }

    }
    catch (e: Exception){
        println("Запрос пошёл по пизде")
        println("Произошла ошибка: $e")
    }
}
