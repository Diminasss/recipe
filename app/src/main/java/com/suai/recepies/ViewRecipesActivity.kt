package com.suai.recepies

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.ByteArrayOutputStream

class ViewRecipesActivity : AppCompatActivity() {
    private val databaseManager = DatabaseManager(this)
    private var newPhoto: String? = null
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_recipes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val myRecipes: Button = findViewById(R.id.myrecipes)
        myRecipes.setOnClickListener {
            finish()
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

        photoRecipesView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        val saveRecipe: Button = findViewById(R.id.save)
        saveRecipe.setOnClickListener {
            val titleRecipesView1: EditText = findViewById(R.id.chooserectext)
            val descriptionRecipesView1: EditText = findViewById(R.id.description)

            val idFromBigTable = intent.getIntExtra("id", 0)
            val newTitle = titleRecipesView1.text.toString()
            val newDescription = descriptionRecipesView1.text.toString()
            val finalPhoto = newPhoto ?: photo // Use newPhoto if it's not null, otherwise use the original photo
            databaseManager.updateRecipeInMyRecipesOnly(idFromBigTable, finalPhoto, newDescription, newTitle)
            val data = mapOf(
                "id" to idFromBigTable.toString(),
                "title" to newTitle,
                "description" to newDescription,
                "photo" to finalPhoto.toString())
            val result: Map<String, Any> = doPostRetroUpdateRecipe(data, "http://10.0.2.2:5000/update_recipe/")
            if (result["result"] == "success"){
                Toast.makeText(this, "Рецепт успешно изменён", Toast.LENGTH_LONG).show()
            } else if (result["result"] == "recipe_not_found"){
                Toast.makeText(this, "Такого рецепта не существует", Toast.LENGTH_LONG).show()
            } else if (result["result"] == "error"){
                Toast.makeText(this, "Непредвиденная ошибка", Toast.LENGTH_LONG).show()
            }
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val uri: Uri = data.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            val photoRecipesView: ImageView = findViewById(R.id.imagerec)
            photoRecipesView.setImageBitmap(bitmap)

            // Convert bitmap to base64
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            newPhoto = Base64.encodeToString(byteArray, Base64.DEFAULT)
        }
    }
}
