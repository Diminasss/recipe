package com.suai.recepies

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

// РЕАЛИЗОВАТЬ ДОБАВЛЕНИЕ РЕЦЕПТА в бд!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
class DatabaseManager(context: Context) {
    val myDBHelper = DatabaseHelper(context)
    var db: SQLiteDatabase? = null

    private fun openDB(){
        db = myDBHelper.writableDatabase
    }
    fun insertUserToDB(login: String, password: String, nickname: String, dateOfBirth: String){
        openDB()

        val values = ContentValues().apply {
            put(MyBDNameClass.COLUMN_NAME_LOGIN, login)
            put(MyBDNameClass.COLUMN_NAME_PASSWORD, password)
            put(MyBDNameClass.COLUMN_NAME_NICKNAME, nickname)
            put(MyBDNameClass.COLUMN_NAME_DATE_OF_BIRTH, dateOfBirth)
        }
        db?.insert(MyBDNameClass.TABLE_NAME, null, values)

        closeDB()
    }
    @SuppressLint("Range")
    fun getUserLoginAndPasswordFromDB(): Map<String, Any> {
        openDB()

        var dataMap: Map<String, Any> = emptyMap() // Инициализация пустым словарем
        val cursor = db?.query(MyBDNameClass.TABLE_NAME, null, null, null, null, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                val loginFromTable = it.getString(it.getColumnIndex(MyBDNameClass.COLUMN_NAME_LOGIN))
                val passwordFromTable = it.getString(it.getColumnIndex(MyBDNameClass.COLUMN_NAME_PASSWORD))
                dataMap = mapOf(
                    "login" to loginFromTable,
                    "password" to passwordFromTable
                )
            }
        }

        closeDB()
        return dataMap
    }

    @SuppressLint("Range")
    fun getLoginFromDB(): String {
        openDB()

        var loginFromTable = ""
        val cursor = db?.query(MyBDNameClass.TABLE_NAME, null, null, null, null, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                loginFromTable = it.getString(it.getColumnIndex(MyBDNameClass.COLUMN_NAME_LOGIN))
            }
        }

        closeDB()
        return loginFromTable
    }

    fun userIsInTable(): Boolean {
        openDB()

        var result = false
        val cursor = db?.query(MyBDNameClass.TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            if (cursor?.moveToFirst() == true) {
                result = true
            }
        }
        cursor?.close()

        closeDB()
        return result
    }

    private fun closeDB(){
        myDBHelper.close()
    }

    fun onUpgradeUser(){
        openDB()

        myDBHelper.onUpgrade(db, 1, 1)

        closeDB()
    }

    fun onUpgradeRecipes(){
        openDB()

        db?.execSQL(MyBDNameClassForRecipes.DELETE_TABLE)
        db?.execSQL(MyBDNameClassForRecipes.CREATE_TABLE)

        closeDB()
    }

    fun addRecipesToDB(recipes: List<Recipe>) {
        openDB()

        recipes.forEach { recipe ->
            val values = ContentValues().apply {
                put(MyBDNameClassForRecipes.COLUMN_NAME_ID_FROM_BIG_TABLE, recipe.id)
                put(MyBDNameClassForRecipes.COLUMN_NAME_TITLE, recipe.title)
                put(MyBDNameClassForRecipes.COLUMN_NAME_CATEGORY, recipe.category)
                put(MyBDNameClassForRecipes.COLUMN_NAME_DESCRIPTION, recipe.description)
                put(MyBDNameClassForRecipes.COLUMN_NAME_PHOTO, recipe.photo)
                put(MyBDNameClassForRecipes.COLUMN_NAME_AUTHOR_LOGIN, recipe.author_login)
                put(MyBDNameClassForRecipes.COLUMN_NAME_AUTHOR_NICK_NAME, recipe.author_nick_name)
            }
            db?.insert(MyBDNameClassForRecipes.TABLE_NAME, null, values)
        }

        closeDB()
    }
//    fun getAllRecipesFromDB(): List<Recipe> {
//        openDB()
//
//        val recipes = mutableListOf<Recipe>()
//        val projection = arrayOf(
//            MyBDNameClassForRecipes.COLUMN_NAME_ID_FROM_BIG_TABLE,
//            MyBDNameClassForRecipes.COLUMN_NAME_TITLE,
//            MyBDNameClassForRecipes.COLUMN_NAME_CATEGORY,
//            MyBDNameClassForRecipes.COLUMN_NAME_DESCRIPTION,
//            MyBDNameClassForRecipes.COLUMN_NAME_PHOTO,
//            MyBDNameClassForRecipes.COLUMN_NAME_AUTHOR_LOGIN,
//            MyBDNameClassForRecipes.COLUMN_NAME_AUTHOR_NICK_NAME
//        )
//        println("Выполнение запроса к базе данных")
//        db?.query(
//            MyBDNameClassForRecipes.TABLE_NAME,
//            projection,
//            null,
//            null,
//            null,
//            null,
//            null
//        )?.use { cursor ->
//            while (cursor.moveToNext()) {
//                val id = cursor.getInt(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_ID_FROM_BIG_TABLE))
//                val title = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_TITLE))
//                val category = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_CATEGORY))
//                val description = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_DESCRIPTION))
//                val photo = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_PHOTO))
//                val author_login = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_AUTHOR_LOGIN))
//                val author_nick_name = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_AUTHOR_NICK_NAME))
//                val recipe = Recipe(id, title, category, description, photo, author_login, author_nick_name)
//                println(recipe)
//                recipes.add(recipe)
//            }
//        }
//        println("Запрос успешно выполнен")
//
//        closeDB()
//        return recipes
//    }

    @SuppressLint("Range")
    fun getAllRecipesFromDB(): List<Recipe> {
        openDB()

        val recipes = mutableListOf<Recipe>()
        val cursor = db?.query(
            MyBDNameClassForRecipes.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_ID_FROM_BIG_TABLE)
                val titleIndex = it.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_TITLE)
                val categoryIndex = it.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_CATEGORY)
                val descriptionIndex = it.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_DESCRIPTION)
                val photoIndex = it.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_PHOTO)
                val authorLoginIndex = it.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_AUTHOR_LOGIN)
                val authorNickNameIndex = it.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_AUTHOR_NICK_NAME)

                do {
                    val recipe = Recipe(
                        id = it.getInt(idIndex),
                        title = it.getString(titleIndex),
                        description = it.getString(descriptionIndex),
                        category = it.getString(categoryIndex),
                        photo = it.getString(photoIndex),
                        author_login = it.getString(authorLoginIndex),
                        author_nick_name = it.getString(authorNickNameIndex)
                    )
                    println(recipe.title)
                    recipes.add(recipe)
                } while (it.moveToNext())
            }
        }

        closeDB()
        return recipes
    }

    fun anyRecipeIsInTable(): Boolean {
        openDB()

        var result = false
        val cursor = db?.query(MyBDNameClassForRecipes.TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            if (cursor?.moveToFirst() == true) {
                result = true
            }
        }
        cursor?.close()

        closeDB()
        return result
    }

    fun getRecipeById(id: Int): Recipe? {
        openDB()

        println("Начало работы getRecipeById с id: $id")
        var recipe: Recipe? = null
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        println("Создана начальная часть запроса")

        db?.query(
            MyBDNameClassForRecipes.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val idFromTable = cursor.getInt(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_ID_FROM_BIG_TABLE))
                val titleFromTable = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_TITLE))
                val categoryFromTable = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_CATEGORY))
                val descriptionFromTable = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_DESCRIPTION))
                val photoFromTable = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_PHOTO))
                val authorLoginFromTable = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_AUTHOR_LOGIN))
                val authorNickNameFromTable = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_AUTHOR_NICK_NAME))

                recipe = Recipe(idFromTable, titleFromTable, descriptionFromTable, categoryFromTable,  photoFromTable, authorLoginFromTable, authorNickNameFromTable)
            }
        }
        println("Выполнен запрос")

        closeDB()
        return recipe
    }



    private fun getAllIds(): List<Int> {
        openDB()

        val ids = mutableListOf<Int>()
        val columns = arrayOf(BaseColumns._ID) // Указываем, что нам нужен только столбец _ID

        try {
            db?.query(
                MyBDNameClassForRecipes.TABLE_NAME,
                columns,   // Запрашиваем только столбец _ID
                null,      // Без условий
                null,      // Без аргументов условий
                null,      // Без группировки
                null,      // Без фильтрации по группам
                null       // Без сортировки
            )?.use { cursor ->
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                    ids.add(id)
                }
            }
        } catch (e: Exception) {
            println("Произошла ошибка: ${e.message}")
            e.printStackTrace()
        }

        closeDB()
        return ids
    }

    // Использование функции и вывод ID
    fun printAllIds() {
        val ids = getAllIds()
        println("Все ID из таблицы:")
        ids.forEach { id ->
            println(id)
        }
    }
}