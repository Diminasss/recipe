package com.suai.recepies

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
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


    }
}