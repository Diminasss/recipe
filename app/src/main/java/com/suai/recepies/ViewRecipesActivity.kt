package com.suai.recepies

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ViewRecipesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_recipes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val myRecipes:Button = findViewById(R.id.myrecipes)
        myRecipes.setOnClickListener{
            val intent = Intent(this, MyRecipesActivity::class.java)
            startActivity(intent)
        }



        val title: String? = intent.getStringExtra("title")
        val photo: String? = intent.getStringExtra("photo")
        val description: String? = intent.getStringExtra("description")

        val titleRecipesView: EditText = findViewById(R.id.chooserectext)
        val descriptionRecipesView: EditText = findViewById(R.id.description)
        val photoRecipesView: ImageView = findViewById(R.id.imagerec)
        titleRecipesView.setText(title)
        descriptionRecipesView.setText(description)
        val imageBytes = Base64.decode(photo, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        photoRecipesView.setImageBitmap(decodedImage)

        println(title)
    }
}