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
import kotlin.concurrent.thread

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
        val a = databaseManager.getAllIdsMyRecipesOnly()
        print("В базе данных было: ${a.size} рецептов\n")
        databaseManager.updateMyRecipesOnlyToDB(recipes)
        val b = databaseManager.getAllIdsMyRecipesOnly()
        print("В базе данных теперь содержится: ${b.size} рецептов\n")

        val recipesContainer: LinearLayout = findViewById(R.id.recipesContainer)
        recipes.forEach { recipe ->
            val recipeView = LayoutInflater.from(this).inflate(R.layout.recipe_item, recipesContainer, false)

            val imageView: ImageView = recipeView.findViewById(R.id.recipe_image)
            val titleView: TextView = recipeView.findViewById(R.id.recipe_title)
            // Задайте изображение и текст для каждого рецепта
            titleView.text = recipe.title
            val imageBytes = Base64.decode(recipe.photo, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            imageView.setImageBitmap(decodedImage)

            recipesContainer.addView(recipeView)
        }
    }
}
