package com.suai.recepies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddRecipesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_recipes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imageRecipe: ImageView = findViewById(R.id.addImageRec)
        val nameRecipe: EditText = findViewById(R.id.nameRecepie)
        val descriptionRecipe: EditText = findViewById(R.id.description)
        val categoryRecipe: EditText = findViewById(R.id.сategoryRecepie)

        val button: Button = findViewById(R.id.add)
        button.setOnClickListener {
            val image = imageRecipe.toString()
            val recipe = nameRecipe.text.toString()
            val description = descriptionRecipe.text.toString()
            val category = categoryRecipe.text.toString()

            if (image.isEmpty() || recipe.isEmpty() || description.isEmpty() || category.isEmpty()){
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            }
            else {
                val addRecipeHTTP: String = "http://10.0.2.2:5000/add_recipe"
                val dictionary: Map<String, String> = mapOf("image" to image, "recipe" to recipe, "description" to description, "category" to category)
                val result = doPost(dictionary, addRecipeHTTP)

                if (result != null){
                    if (result["result"] == "success") {
                        Toast.makeText(this, "Рецепт $recipe добавлен", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MenuActivity::class.java)
                        startActivity(intent)
                    }
                    else if (result["result"] == "user_is_not_in_table") {
                        Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_LONG).show()
                    }
                }
                nameRecipe.text.clear()
                descriptionRecipe.text.clear()
                categoryRecipe.text.clear()
            }
        }
    }
}

