package com.suai.recepies

import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import retrofit2.http.Body


interface RecipeApiService {
    @POST("/get_six_random_recipes")
    suspend fun getSixRandomRecipes(): RecipeResponse

    @POST("/get_users_recipes")
    suspend fun getUsersRecipes(@Body request: Map<String, String>): Response<RecipeResponse>

    @POST("/update_recipe")
    suspend fun updateRecipe(@Body request: Map<String, String>): Response<Map<String, Any>>
}

data class RecipeResponse(
    val result: List<Recipe>
)

data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val photo: String,
    val author_login: String,
    val author_nick_name: String
)

fun doPostRetroMainMenu(http: String): List<Recipe> = runBlocking {
    val retrofit = Retrofit.Builder()
        .baseUrl(http)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(RecipeApiService::class.java)
    var resultList: List<Recipe> = emptyList()

    try {
        val recipeResponse = service.getSixRandomRecipes()
        resultList = recipeResponse.result
    } catch (e: Exception) {
        // Handle the error here
    }
    resultList
}

fun doPostRetroMyRecipes(login: String, http: String): List<Recipe> = runBlocking {
    val retrofit = Retrofit.Builder()
        .baseUrl(http)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(RecipeApiService::class.java)
    var resultList: List<Recipe> = emptyList()

    try {
        val request = mapOf("login" to login)
        val recipeResponse = service.getUsersRecipes(request)
        if (recipeResponse.isSuccessful) {
            resultList = recipeResponse.body()?.result ?: emptyList()
        }
    } catch (e: Exception) {
        // Handle the error here
    }
    resultList
}

fun doPostRetroUpdateRecipe(recipeData: Map<String, String>, http: String): Map<String, Any> = runBlocking {
    val retrofit = Retrofit.Builder()
        .baseUrl(http)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(RecipeApiService::class.java)
    var resultMap: Map<String, Any> = emptyMap()

    try {
        val response = service.updateRecipe(recipeData)
        if (response.isSuccessful) {
            resultMap = response.body() ?: emptyMap()
        }
    } catch (e: Exception) {
        // Handle the error here
    }
    resultMap
}