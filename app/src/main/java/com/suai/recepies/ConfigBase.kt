package com.suai.recepies

import okhttp3.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class Registration {

    private val serverHTTP: String = "http://127.0.0.1:5000/test"

    fun register(login: String, password: String, nickname: String, dateOfBirth: String): String? {
        val client = OkHttpClient()
        println("Создан клиент")

        // Создание JSON объекта
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        println("Моши создан")
        val jsonAdapter = moshi.adapter(User::class.java)
        val user = User(login, password, nickname, dateOfBirth)
        val json = jsonAdapter.toJson(user)
        println("Создан json: $json")

        // Создание тела запроса
        val body = json.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url(serverHTTP)
            .post(body)
            .build()
        println("Создан запрос")

        var responseString: String? = null

        // Выполнение запроса
        try {
            val response = client.newCall(request).execute()
            println("Запрос выполнен")
            if (!response.isSuccessful) {
                println("Ошибка ответа: ${response.code}")
                throw IOException("Unexpected code $response")
            }
            responseString = response.body?.string()
            println("Получен ответ: $responseString")
        } catch (e: IOException) {
            println("Ошибка при выполнении запроса: ${e.message}")
            e.printStackTrace()
        }

        println("Запрос окончен")
        return responseString
    }

    data class User(val login: String, val password: String, val nickname: String, val dateOfBirth: String)
}