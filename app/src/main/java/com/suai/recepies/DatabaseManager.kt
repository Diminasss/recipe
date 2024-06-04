package com.suai.recepies

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DatabaseManager(context: Context) {
    val myDBHelper = DatabaseHelper(context)
    var db: SQLiteDatabase? = null

    fun openDB(){
        db = myDBHelper.writableDatabase
    }
}