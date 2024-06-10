package com.suai.recepies
//
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.runBlocking
//
//interface RecipeApiService {
//    @POST("/get_six_random_recipes")
//    fun getSixRandomRecipes(): Call<RecipeResponse>
//}
//
//data class RecipeResponse(
//    val result: List<Recipe>
//)
//
//data class Recipe(
//    val id: Int,
//    val title: String,
//    val description: String,
//    val category: String,
//    val photo: String,
//    val author_login: String,
//    val author_nick_name: String
//)
//
//fun doPostRetro(http: String): MutableList<Map<String, Any>> {
//    val retrofit = Retrofit.Builder()
//        .baseUrl(http)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val service = retrofit.create(RecipeApiService::class.java)
//    val call = service.getSixRandomRecipes()
//    val resultList = mutableListOf<Map<String, Any>>()
//    call.enqueue(object : Callback<RecipeResponse> {
//        override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
//            if (response.isSuccessful) {
//
//                val recipeResponse = response.body()
//                if (recipeResponse != null) {
//
//                    for (x in recipeResponse.result){
//                        val newMap: Map<String, Any> = mapOf(
//                            "id" to x.id,
//                            "title" to x.title,
//                            "photo" to x.photo,
//                            "category" to x.category,
//                            "description" to x.description,
//                            "author_login" to x.author_login,
//                            "author_nick_name" to x.author_nick_name
//                        )
//                        resultList.add(newMap)
//
//                    }
//
//                }
//
//                // Handle the successful response here
//            }
//            else {
//                // Handle the error response here
//            }
//        }
//
//        override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
//
//        }
//    })
//
//    return resultList
//}
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
//fun doPostRetroMainMenu(http: String): MutableList<Map<String, Any>> = runBlocking {
//    val retrofit = Retrofit.Builder()
//        .baseUrl(http)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val service = retrofit.create(RecipeApiService::class.java)
//    val resultList = mutableListOf<Map<String, Any>>()
//
//    try {
//        val recipeResponse = service.getSixRandomRecipes()
//        for (x in recipeResponse.result) {
//            val newMap: Map<String, Any> = mapOf(
//                "id" to x.id,
//                "title" to x.title,
//                "photo" to x.photo,
//                "category" to x.category,
//                "description" to x.description,
//                "author_login" to x.author_login,
//                "author_nick_name" to x.author_nick_name
//            )
//            resultList.add(newMap)
//        }
//    } catch (e: Exception) {
//        // Handle the error here
//    }
//
//    resultList
//}

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