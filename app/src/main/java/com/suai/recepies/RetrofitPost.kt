package com.suai.recepies

import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.runBlocking


interface RecipeApiService {
    @POST("/get_six_random_recipes")
    suspend fun getSixRandomRecipes(): RecipeResponse
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