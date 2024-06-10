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


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val addRecipesButton: Button = findViewById(R.id.nextRecipeList)
        addRecipesButton.setOnClickListener {
            val intent = Intent(this, AddRecipesActivity::class.java)
            startActivity(intent)
        }

        val myRecipesButton: Button = findViewById(R.id.myrecipes)
        myRecipesButton.setOnClickListener {
            val intent = Intent(this, AddRecipesActivity::class.java)
            startActivity(intent)
        }

        val logInHTTP = "http://10.0.2.2:5000/get_six_random_recipes/"
        val result: List<Recipe> = doPostRetroMainMenu(logInHTTP)
        loadRecipes(result)
    }

    private fun loadRecipes(result: List<Recipe>?) {
        thread {
            runOnUiThread {
                if (result != null) {

                    displayRecipes(result)
                }
            }
        }
    }
    private fun displayRecipes(recipes: List<Recipe>) {
        val recipeViews = listOf(
            Pair(findViewById<TextView>(R.id.res1), findViewById<ImageView>(R.id.image1)),
            Pair(findViewById<TextView>(R.id.res2), findViewById<ImageView>(R.id.image2)),
            Pair(findViewById<TextView>(R.id.res3), findViewById<ImageView>(R.id.image3)),
            Pair(findViewById<TextView>(R.id.res4), findViewById<ImageView>(R.id.image4)),
            Pair(findViewById<TextView>(R.id.res5), findViewById<ImageView>(R.id.image5)),
            Pair(findViewById<TextView>(R.id.res6), findViewById<ImageView>(R.id.image6))
        )

        recipes.forEachIndexed { index, recipe ->
            val (textView, imageView) = recipeViews[index]
            textView.text = recipe.title

            val decodedString = Base64.decode(recipe.photo, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            imageView.setImageBitmap(decodedByte)
        }
    }
}

