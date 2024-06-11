package com.suai.recepies


import android.provider.BaseColumns

object MyBDNameClass{
    const val TABLE_NAME = "user"
    const val COLUMN_NAME_LOGIN = "login"
    const val COLUMN_NAME_PASSWORD = "password"
    const val COLUMN_NAME_NICKNAME = "nick_name"
    const val COLUMN_NAME_DATE_OF_BIRTH = "date_of_birth"
    const val COLUMN_NAME_RECIPES_OWNER = "recipes_owner"

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

object MyBDNameClassForRecipes{
    const val TABLE_NAME = "six_recipes"
    const val COLUMN_NAME_ID_FROM_BIG_TABLE = "id_from_big_table"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_CATEGORY = "category"
    const val COLUMN_NAME_DESCRIPTION = "description"
    const val COLUMN_NAME_PHOTO = "photo"
    const val COLUMN_NAME_AUTHOR_LOGIN = "author_login"
    const val COLUMN_NAME_AUTHOR_NICK_NAME = "author_nick_name"


    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "database.sqlite"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME(" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_ID_FROM_BIG_TABLE INTEGER," +
            "$COLUMN_NAME_TITLE TEXT," +
            "$COLUMN_NAME_CATEGORY TEXT," +
            "$COLUMN_NAME_DESCRIPTION TEXT," +
            "$COLUMN_NAME_PHOTO TEXT," +
            "$COLUMN_NAME_AUTHOR_LOGIN TEXT," +
            "$COLUMN_NAME_AUTHOR_NICK_NAME TEXT)"

    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}