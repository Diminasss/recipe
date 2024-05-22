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

//import okhttp3.*
//import okhttp3.MediaType.Companion.toMediaType
//import okhttp3.RequestBody.Companion.toRequestBody
//import org.json.JSONObject
//import java.io.IOException

//class Registration {
//
//    private val serverHTTP: String = "http://127.0.0.1:5000/test"
//
//    fun register(login: String, password: String, nickname: String, dateOfBirth: String): String? {
//        val client = OkHttpClient()
//        println("Создан клиент")
//
//        // Создание JSON объекта
//        val jsonObject = JSONObject()
//            .put("login", login)
//            .put("password", password)
//            .put("nickname", nickname)
//            .put("dateOfBirth", dateOfBirth)
//
//        val json = JSONObject.quote(jsonObject.toString())
//
//        println("Создан json: $jsonObject")
//
//        // Создание тела запроса
//        val body = json.toRequestBody("application/json; charset=utf-8".toMediaType())
//
//        // Создание запроса
//        val request = Request.Builder().url(serverHTTP).post(body).build()
//
//        println("Создан запрос")
//
//        var responseString: String? = null
//
//        // Выполнение запроса
//        try {
//            val response = client.newCall(request).execute()
//            println("Запрос выполнен")
//            if (!response.isSuccessful) {
//                println("Ошибка ответа: ${response.code}")
//                throw IOException("Unexpected code $response")
//            }
//            responseString = response.body?.string()
//            println("Получен ответ: $responseString")
//        } catch (e: Exception) {
//            println("Ошибка при выполнении запроса: ${e.message}")
//            e.printStackTrace()
//        }
//
//        println("Запрос окончен")
//        return responseString
//    }
//}

class Registration{
    private val serverHTTP: String = "http://127.0.0.1:5000/test"

    @WorkerThread
    fun register(login: String, password: String, nickname: String, dateOfBirth: String) {

        // Создание данных и сериализация
        val dictionary: Map<String, String> = mapOf("login" to login, "password" to password, "nick_name" to nickname, "date_of_birth" to dateOfBirth)
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(Map::class.java)

        // Получение полноценного JSON и метаданных к нему
        val jsonRequest = adapter.toJson(dictionary)
        val JSON = "application/json; charset=utf-8".toMediaType()

        // Создание клиента и формирование запроса
        val client = OkHttpClient()
        val body: RequestBody = jsonRequest.toRequestBody(JSON)
        val request = Request.Builder().url(serverHTTP).post(body).build()

        // Попытка проведения Post запроса
        try{
            client.newCall(request).execute().use {response ->
                if (!response.isSuccessful){
                    throw IOException("Запрос к серверу не был успешным")
                }
                println(response.body!!.string())
            }
        }
        catch (e: Exception){
            println("Произошла ошибка: $e")
        }
    }
}