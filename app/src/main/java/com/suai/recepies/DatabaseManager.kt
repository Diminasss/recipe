package com.suai.recepies

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

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

    fun dropTable() {
        db?.execSQL(MyBDNameClass.DELETE_TABLE)
    }

    fun onUpgrade(){
        myDBHelper.onUpgrade(db, 1, 1)
    }
}