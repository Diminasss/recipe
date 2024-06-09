package com.suai.recepies

import android.annotation.SuppressLint
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
    }
}