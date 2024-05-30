package com.suai.recepies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.widget.Toast


class Items : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_items)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        // Создание БД для фото и названия рецептов в Items
        val imageDatabase = mapOf(
            "Image 1" to "res/drawable/lasania.jpeg",
            "Image 2" to "/path/to/image2.jpg",
            "Image 3" to "/path/to/image3.jpg"
        )
        fun getImageFromDatabase(imageName: String):Bitmap? {
            val imagePath = imageDatabase[imageName]
            if (imagePath == null) {
                return null
            }
            val imageBitmap = BitmapFactory.decodeFile(imagePath)
            return imageBitmap
        }
        fun getNameFromDatabase(imageName: String): String? {
            val imagePath = imageDatabase[imageName]
            if (imagePath == null) {
                return null
            }
            return imageName
        }





        val addResipesButton: Button = findViewById(R.id.addRecipe)
        addResipesButton.setOnClickListener {
            val intent = Intent(this, AddRecipesActivity::class.java)
            startActivity(intent)
        }


    }
}