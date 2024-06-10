package com.suai.recepies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)


        val logInHTTP = "http://10.0.2.2:5000/get_six_random_recipes/"
        val rez = doPostRetroMainMenu(logInHTTP)



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


        //loadRecipes(rez)
    }

//    private fun loadRecipes(result: Map<String, Any>?) {
//        thread {
//            runOnUiThread {
//                if (result != null) {
//                    val recipes = parseRecipes(result)
//                    displayRecipes(recipes)
//                }
//            }
//        }
//    }
//
//    private fun parseRecipes(result: Map<String, Any>): List<Recipe> {
//        val recipes = mutableListOf<Recipe>()
//        val resultList = result["result"] as? List<Map<String, Any>> ?: return recipes
//        for (item in resultList) {
//            val recipe = Recipe(
//                author_login = item["author_login"] as String,
//                author_nick_name = item["author_nick_name"] as String,
//                category = item["category"] as String,
//                description = item["description"] as String,
//                id = (item["id"] as Double),
//                photo = item["photo"] as String,
//                title = item["title"] as String
//            )
//            recipes.add(recipe)
//        }
//        return recipes
//    }
//
//    private fun displayRecipes(recipes: List<Recipe>) {
//        val recipeViews = listOf(
//            Pair(findViewById<TextView>(R.id.res1), findViewById<ImageView>(R.id.image1)),
//            Pair(findViewById<TextView>(R.id.res2), findViewById<ImageView>(R.id.image2)),
//            Pair(findViewById<TextView>(R.id.res3), findViewById<ImageView>(R.id.image3)),
//            Pair(findViewById<TextView>(R.id.res4), findViewById<ImageView>(R.id.image4)),
//            Pair(findViewById<TextView>(R.id.res5), findViewById<ImageView>(R.id.image5)),
//            Pair(findViewById<TextView>(R.id.res6), findViewById<ImageView>(R.id.image6))
//        )
//
//        recipes.forEachIndexed { index, recipe ->
//            val (textView, imageView) = recipeViews[index]
//            textView.text = recipe.title
//
//            val decodedString = Base64.decode(recipe.photo, Base64.DEFAULT)
//            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
//            imageView.setImageBitmap(decodedByte)
//        }
//    }
//
//    // Не забудьте реализовать doGet в другом месте
}
//
//data class Recipe(
//    val author_login: String,
//    val author_nick_name: String,
//    val category: String,
//    val description: String,
//    val id: Double,
//    val photo: String,
//    val title: String
//)
