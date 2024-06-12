package com.suai.recepies

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MyRecipesActivity : AppCompatActivity() {
    private val databaseManager = DatabaseManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_recipes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val addRecipesMoreButton: Button = findViewById(R.id.addRecipeMore)
        addRecipesMoreButton.setOnClickListener {
            val intent = Intent(this, AddRecipesActivity::class.java)
            startActivity(intent)
        }

        val login: String = databaseManager.getLoginFromDB()
        val recipes: List<Recipe> = doPostRetroMyRecipes(login, "http://10.0.2.2:5000/get_users_recipes/")
        println("Получено с сервера моих рецептов ${recipes.size}")
        databaseManager.updateMyRecipesOnlyToDB(recipes)

        //sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss

        val myRecipesOnlyIDs = databaseManager.getAllIdsMyRecipesOnly() // Здесь хранятся все ID моих рецептов
        print("В базе данных теперь содержится: ${myRecipesOnlyIDs.size} рецептов\n")
        val bigIdList: MutableList<List<Int?>> = mutableListOf()
        val idList: MutableList<Int?> = mutableListOf()

        for (id in myRecipesOnlyIDs) {
            idList.add(id)
            if (idList.size == 3) {
                bigIdList.add(idList.toList()) // Используем toList() для создания копии списка
                idList.clear()
            }
        }

        if (idList.isNotEmpty()) {
            while (idList.size != 3){
                idList.add(null)
            }
            bigIdList.add(idList.toList()) // Используем toList() для создания копии списка
        }

        if (bigIdList.isNotEmpty()){
            val recipesContainer: LinearLayout = findViewById(R.id.recipesContainer)
            for (triada in bigIdList){

                val first: Int? = triada[0]
                val second: Int? = triada[1]
                val third: Int? = triada[2]

                val recipeView = LayoutInflater.from(this).inflate(R.layout.recipe_item, recipesContainer, false)

                val recipe1: Recipe? = databaseManager.getRecipeById(first!!.toInt())
                val imageView1: ImageView = recipeView.findViewById(R.id.recipe_image1)
                val titleView1: TextView = recipeView.findViewById(R.id.recipe_title1)
                titleView1.text = recipe1?.title

                val imageBytes1 = Base64.decode(recipe1?.photo, Base64.DEFAULT)
                val decodedImage1 = BitmapFactory.decodeByteArray(imageBytes1, 0, imageBytes1.size)
                imageView1.setImageBitmap(decodedImage1)


                if (second != null){
                    val recipe2: Recipe? = databaseManager.getRecipeById(second)
                    val imageView2: ImageView = recipeView.findViewById(R.id.recipe_image2)
                    val titleView2: TextView = recipeView.findViewById(R.id.recipe_title2)
                    titleView2.text = recipe2?.title

                    val imageBytes2 = Base64.decode(recipe2?.photo, Base64.DEFAULT)
                    val decodedImage2 = BitmapFactory.decodeByteArray(imageBytes2, 0, imageBytes2.size)
                    imageView2.setImageBitmap(decodedImage2)
                }

                if (third != null){
                    val recipe3: Recipe? = databaseManager.getRecipeById(third)
                    val imageView3: ImageView = recipeView.findViewById(R.id.recipe_image3)
                    val titleView3: TextView = recipeView.findViewById(R.id.recipe_title3)
                    titleView3.text = recipe3?.title

                    val imageBytes3 = Base64.decode(recipe3?.photo, Base64.DEFAULT)
                    val decodedImage3 = BitmapFactory.decodeByteArray(imageBytes3, 0, imageBytes3.size)
                    imageView3.setImageBitmap(decodedImage3)
                }
                recipesContainer.addView(recipeView)
            }
        }




        //ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc
//        val recipesContainer: LinearLayout = findViewById(R.id.recipesContainer)
//        recipes.forEach { recipe ->
//            val recipeView = LayoutInflater.from(this).inflate(R.layout.recipe_item, recipesContainer, false)

//            val imageView: ImageView = recipeView.findViewById(R.id.recipe_image1)
//            val titleView: TextView = recipeView.findViewById(R.id.recipe_title1)
            // Задайте изображение и текст для каждого рецепта
//            titleView.text = recipe.title
//            val imageBytes = Base64.decode(recipe.photo, Base64.DEFAULT)
//            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//            imageView.setImageBitmap(decodedImage)
//
//            recipesContainer.addView(recipeView)
        //}
    }
}
