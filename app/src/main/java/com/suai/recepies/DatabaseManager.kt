package com.suai.recepies

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
// РЕАЛИЗОВАТЬ ДОБАВЛЕНИЕ РЕЦЕПТА в бд!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
class DatabaseManager(context: Context) {
    val myDBHelper = DatabaseHelper(context)
    var db: SQLiteDatabase? = null

    fun openDB(){
        db = myDBHelper.writableDatabase
    }
    fun insertUserToDB(login: String, password: String, nickname: String, dateOfBirth: String, recipesOwner: String){
        val values = ContentValues().apply {
            put(MyBDNameClass.COLUMN_NAME_LOGIN, login)
            put(MyBDNameClass.COLUMN_NAME_PASSWORD, password)
            put(MyBDNameClass.COLUMN_NAME_NICKNAME, nickname)
            put(MyBDNameClass.COLUMN_NAME_DATE_OF_BIRTH, dateOfBirth)
            put(MyBDNameClass.COLUMN_NAME_RECIPES_OWNER, recipesOwner)
        }
        db?.insert(MyBDNameClass.TABLE_NAME, null, values)
    }
    @SuppressLint("Range")
    fun getUserLoginAndPasswordFromDB(): Map<String, Any> {
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
        return dataMap
    }

    @SuppressLint("Range")
    fun getLoginFromDB(): String {
        var loginFromTable = ""
        val cursor = db?.query(MyBDNameClass.TABLE_NAME, null, null, null, null, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                loginFromTable = it.getString(it.getColumnIndex(MyBDNameClass.COLUMN_NAME_LOGIN))
            }
        }
        return loginFromTable
    }

    fun userIsInTable(): Boolean {
        var result = false
        val cursor = db?.query(MyBDNameClass.TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            if (cursor?.moveToFirst() == true) {
                result = true
            }
        }
        cursor?.close()
        return result
    }

    fun closeDB(){
        myDBHelper.close()
    }

    fun onUpgradeUser(){
        myDBHelper.onUpgrade(db, 1, 1)
    }

    fun onUpgradeRecipes(){
        db?.execSQL(MyBDNameClassForRecipes.DELETE_TABLE)
        db?.execSQL(MyBDNameClassForRecipes.CREATE_TABLE)
    }

    fun addRecipesToDB(recipes: List<Recipe>) {
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
    }
    fun getAllRecipesFromDB(): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        val projection = arrayOf(
            MyBDNameClassForRecipes.COLUMN_NAME_ID_FROM_BIG_TABLE,
            MyBDNameClassForRecipes.COLUMN_NAME_TITLE,
            MyBDNameClassForRecipes.COLUMN_NAME_CATEGORY,
            MyBDNameClassForRecipes.COLUMN_NAME_DESCRIPTION,
            MyBDNameClassForRecipes.COLUMN_NAME_PHOTO,
            MyBDNameClassForRecipes.COLUMN_NAME_AUTHOR_LOGIN,
            MyBDNameClassForRecipes.COLUMN_NAME_AUTHOR_NICK_NAME
        )
        db?.query(
            MyBDNameClassForRecipes.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_ID_FROM_BIG_TABLE))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_TITLE))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_CATEGORY))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_DESCRIPTION))
                val photo = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_PHOTO))
                val author_login = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_AUTHOR_LOGIN))
                val author_nick_name = cursor.getString(cursor.getColumnIndexOrThrow(MyBDNameClassForRecipes.COLUMN_NAME_AUTHOR_NICK_NAME))
                val recipe = Recipe(id, title, category, description, photo, author_login, author_nick_name)
                recipes.add(recipe)
            }
        }
        return recipes
    }

    fun anyRecipeIsInTable(): Boolean {
        var result = false
        val cursor = db?.query(MyBDNameClassForRecipes.TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            if (cursor?.moveToFirst() == true) {
                result = true
            }
        }
        cursor?.close()
        return result
    }
}