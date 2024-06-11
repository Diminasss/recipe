package com.suai.recepies


import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView

class AnotherViewRecipesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_another_view_recipes)

        val title: String? = intent.getStringExtra("title")
        val photo: String? = intent.getStringExtra("photo")
        val nickName: String? = intent.getStringExtra("author_nick_name")
        val category: String? = intent.getStringExtra("category")
        val description: String? = intent.getStringExtra("description")


        val titleView: TextView = findViewById(R.id.chooserectext)
        titleView.text = title

        val photoView: ImageView = findViewById(R.id.imagerec)
        val decodedString = Base64.decode(photo, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        photoView.setImageBitmap(decodedByte)

        val nickNameView: TextView = findViewById(R.id.nick_name_recipe)
        nickNameView.text = nickName

        val categoryView: TextView = findViewById(R.id.сategoryRecipe)
        categoryView.text = category

        val descriptionTextView: TextView = findViewById(R.id.textView)
        descriptionTextView.text = description


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val myRecipes: Button = findViewById(R.id.allMyRecipes)
        myRecipes.setOnClickListener{
            finish()
        }
    }
}