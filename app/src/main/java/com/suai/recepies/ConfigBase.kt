package com.suai.recepies

import androidx.annotation.WorkerThread
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

fun doPost(mapToJSON: Map<String, Any>, inHTTP: String): Map<String, Any>? {
    @WorkerThread
    fun underFunction(dictionary: Map<String, Any>, http: String): Map<String, Any>? {

        // Создание данных и сериализация

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(Map::class.java)

        // Получение полноценного JSON и метаданных к нему
        val jsonRequest = adapter.toJson(dictionary)
        val JSON = "application/json; charset=utf-8".toMediaType()


        // Создание клиента и формирование запроса
        val client = OkHttpClient()
        val body: RequestBody = jsonRequest.toRequestBody(JSON)
        val request = Request.Builder().url(http).post(body).build()


        // Попытка проведения Post запроса
        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {

                    throw IOException("Запрос к серверу не был успешным")
                }
                val responseAdapter = moshi.adapter<Map<String, Any>>(Map::class.java)
                val registerResponse: Map<String, Any>? =
                    responseAdapter.fromJson(response.body!!.string())
                return registerResponse
            }

        } catch (e: Exception) {
            println("Запрос пошёл по нн-ому месту")
            println("Произошла ошибка: $e")
            return mapOf("result" to "Exception")
        }
    }
    // Создаем корутину в блоке runBlocking
    val result = runBlocking {
        // Запускаем корутину с помощью launch и передаем контекст Dispatchers.IO,
        // чтобы выполнить операцию ввода-вывода (в вашем случае - сетевой запрос) в фоновом потоке
        val deferredResult = async(Dispatchers.IO) {
            // Вызываем функцию register() в контексте корутины
            underFunction(mapToJSON, inHTTP)
        }
        deferredResult.await()
    }
    return result
}

fun doGet(inHTTP: String): Map<String, Any>? {
    @WorkerThread
    fun underFunction(http: String): Map<String, Any>? {
        // Создание клиента и формирование запроса
        val client = OkHttpClient()
        val request = Request.Builder().url(http).build()

        // Попытка проведения GET запроса
        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Запрос к серверу не был успешным")
                }

                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val responseAdapter = moshi.adapter<Map<String, Any>>(Map::class.java)
                val responseBody = response.body?.string()
                val resultResponse: Map<String, Any>? =
                    responseBody?.let { responseAdapter.fromJson(it) }
                return resultResponse
            }
        } catch (e: Exception) {
            println("Get запрос пошёл по нн-ому месту  из-за $e")
            return mapOf("result" to "Exception")
        }
    }

    // Создаем корутину в блоке runBlocking
    val result = runBlocking {
        // Запускаем корутину с помощью launch и передаем контекст Dispatchers.IO,
        // чтобы выполнить операцию ввода-вывода (в вашем случае - сетевой запрос) в фоновом потоке
        val deferredResult = async(Dispatchers.IO) {
            // Вызываем функцию underFunction() в контексте корутины
            underFunction(inHTTP)
        }
        deferredResult.await()
    }
    return result
}


