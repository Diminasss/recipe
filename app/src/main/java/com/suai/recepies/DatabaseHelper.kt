package com.suai.recepies

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, MyBDNameClass.DATABASE_NAME, null, MyBDNameClass.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(MyBDNameClass.CREATE_TABLE)
        db?.execSQL(MyBDNameClassForRecipes.CREATE_TABLE)
        db?.execSQL(MyDBNameClassForMyRecipesOnly.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(MyBDNameClass.DELETE_TABLE)
        db?.execSQL(MyBDNameClassForRecipes.DELETE_TABLE)
        db?.execSQL(MyDBNameClassForMyRecipesOnly.DELETE_TABLE)
        onCreate(db)
    }

}