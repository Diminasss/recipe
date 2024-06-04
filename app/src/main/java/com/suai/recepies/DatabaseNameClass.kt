package com.suai.recepies


import android.provider.BaseColumns

object MyBDNameClass{
    private const val TABLE_NAME = "user"
    private const val COLUMN_NAME_LOGIN = "login"
    private const val COLUMN_NAME_PASSWORD = "password"
    private const val COLUMN_NAME_NICKNAME = "nick_name"
    private const val COLUMN_NAME_DATE_OF_BIRTH = "date_of_birth"
    private const val COLUMN_NAME_RECIPES_OWNER = "recipes_owner"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "database.sqlite"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME(" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_LOGIN TEXT," +
            "$COLUMN_NAME_PASSWORD TEXT," +
            "$COLUMN_NAME_NICKNAME TEXT," +
            "$COLUMN_NAME_DATE_OF_BIRTH TEXT," +
            "$COLUMN_NAME_RECIPES_OWNER TEXT)"

    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}