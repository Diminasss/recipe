package com.suai.recepies

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Scroller
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.ByteArrayOutputStream

class AddRecipesActivity : AppCompatActivity() {
    private var imageUri: Uri? = null
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>
    private var imageBase64: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_recipes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Запрос разрешений на доступ к внешнему хранилищу
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }

        val recipePhoto: ImageView = findViewById(R.id.addImageRec)
        val recipeTitle: EditText = findViewById(R.id.nameRecepie)
        val recipeCategory: EditText = findViewById(R.id.сategoryRecipe)
        val recipeDescription: EditText = findViewById(R.id.description)
        recipeDescription.setScroller(Scroller(this))
        recipeDescription.isVerticalScrollBarEnabled = true
        recipeDescription.movementMethod = ScrollingMovementMethod()

        // Инициализация ActivityResultLauncher для выбора изображения
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
                recipePhoto.setImageURI(uri)
                imageBase64 = convertImageViewToBase64(recipePhoto)
            }
        }

        recipePhoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        val addButton: Button = findViewById(R.id.add)
        addButton.setOnClickListener {
            val title = recipeTitle.text.toString()
            val description = recipeDescription.text.toString()
            val category = recipeCategory.text.toString()
            val databaseManager = DatabaseManager(this)

            if (imageBase64 == null || title.isEmpty() || description.isEmpty() || category.isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            } else {
                val addRecipeHTTP = "http://10.0.2.2:5000/add_recipe"
                val recipe: Map<String, String> = mapOf(
                    "photo" to imageBase64!!, // Сохранение base64 строки
                    "title" to title,
                    "description" to description,
                    "category" to category
                )

                val login: String = databaseManager.getLoginFromDB()

                val mapp = mutableMapOf(
                    "login" to login,
                    "recipe" to recipe
                )
                val result = doPost(mapp, addRecipeHTTP)

                if (result != null) {
                    if (result["result"] == "success") {
                        Toast.makeText(this, "Рецепт $title добавлен", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MenuActivity::class.java)
                        startActivity(intent)
                    } else if (result["result"] == "user_is_not_in_table") {
                        Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_LONG).show()
                    }
                }
                recipeTitle.text.clear()
                recipeDescription.text.clear()
                recipeCategory.text.clear()
            }
        }
    }

    // Функция для конвертации ImageView в base64 строку
    private fun convertImageViewToBase64(imageView: ImageView): String {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}
