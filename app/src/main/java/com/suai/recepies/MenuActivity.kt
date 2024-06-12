package com.suai.recepies

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.concurrent.thread


class MenuActivity : AppCompatActivity() {
    private lateinit var recipeViews: List<Pair<TextView, ImageView>>
    private val databaseManager = DatabaseManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        databaseManager.printAllIds()
        if (databaseManager.anyRecipeIsInTable()){
            thread {
                Thread.sleep(500)
                val recipesFromAndroidDB: List<Recipe> = databaseManager.getAllRecipesFromDB()

                runOnUiThread {
                    displayRecipes(recipesFromAndroidDB)
                }
            }
        } else {

            loadRecipes()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recipeViews = listOf(
            Pair(findViewById<TextView>(R.id.res1), findViewById<ImageView>(R.id.image1)),
            Pair(findViewById<TextView>(R.id.res2), findViewById<ImageView>(R.id.image2)),
            Pair(findViewById<TextView>(R.id.res3), findViewById<ImageView>(R.id.image3)),
            Pair(findViewById<TextView>(R.id.res4), findViewById<ImageView>(R.id.image4)),
            Pair(findViewById<TextView>(R.id.res5), findViewById<ImageView>(R.id.image5)),
            Pair(findViewById<TextView>(R.id.res6), findViewById<ImageView>(R.id.image6))
        )


        findViewById<ConstraintLayout>(R.id.container1).setOnClickListener {
            getRecipeFromAndroidDB(1)
        }

        findViewById<ConstraintLayout>(R.id.container2).setOnClickListener{
            getRecipeFromAndroidDB(2)
        }

        findViewById<ConstraintLayout>(R.id.container3).setOnClickListener{
            getRecipeFromAndroidDB(3)
        }

        findViewById<ConstraintLayout>(R.id.container4).setOnClickListener{
            getRecipeFromAndroidDB(4)
        }

        findViewById<ConstraintLayout>(R.id.container5).setOnClickListener{
            getRecipeFromAndroidDB(5)
        }

        findViewById<ConstraintLayout>(R.id.container6).setOnClickListener{
            getRecipeFromAndroidDB(6)
        }


        val addRecipesButton: Button = findViewById(R.id.nextRecipeList)
        addRecipesButton.setOnClickListener {
            loadRecipes()
            Toast.makeText(this, "Новая подборка!", Toast.LENGTH_LONG).show()
        }
        val myRecipesButton: Button = findViewById(R.id.myrecipes)
        myRecipesButton.setOnClickListener {
            val intent = Intent(this, MyRecipesActivity::class.java)
            startActivity(intent)
        }
    }



    private fun loadRecipes(logInHTTP: String = "http://10.0.2.2:5000/get_six_random_recipes/") {

        thread {
            val result: List<Recipe> = doPostRetroMainMenu(logInHTTP)
            Thread.sleep(500)

            runOnUiThread {
                displayRecipes(result)
            }
            if (result.isNotEmpty()) {

                databaseManager.onUpgradeRecipes()
                databaseManager.addRecipesToDB(result)

            }
        }

    }

    private fun displayRecipes(recipes: List<Recipe>) {

        recipes.forEachIndexed { index, recipe ->
            val (textView, imageView) = recipeViews[index]
            textView.text = recipe.title


            val decodedString = Base64.decode(recipe.photo, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            imageView.setImageBitmap(decodedByte)
        }
    }

    private fun getRecipeFromAndroidDB(number: Int){
        val res = databaseManager.getRecipeById(number)
        if (res != null) {
            val anotherViewIntent = Intent(this, AnotherViewRecipesActivity::class.java)
            anotherViewIntent.putExtra("title", res.title)
            anotherViewIntent.putExtra("photo", res.photo)
            anotherViewIntent.putExtra("author_nick_name", res.author_nick_name)
            anotherViewIntent.putExtra("description", res.description)
            anotherViewIntent.putExtra("category", res.category)
            startActivity(anotherViewIntent)
        }
    }
}