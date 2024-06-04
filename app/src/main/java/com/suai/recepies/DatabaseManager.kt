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
    fun getUserFromDB(): ArrayList<String>{
        val dataList = ArrayList<String>()
        val cursor = db?.query(MyBDNameClass.TABLE_NAME, null, null, null, null, null, null)

        with(cursor){
            while (cursor?.moveToNext()!!){
                val dataText = cursor.getString(cursor.getColumnIndex(MyBDNameClass.COLUMN_NAME_LOGIN))
                dataList.add(dataText.toString())
            }
        }
        cursor?.close()

        return dataList
    }

    fun closeDB(){
        myDBHelper.close()
    }
}