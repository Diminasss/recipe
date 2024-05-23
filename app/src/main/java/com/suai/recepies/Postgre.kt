package com.suai.recepies

import android.os.StrictMode
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

object DatabaseHelper {

    private const val JDBC_URL = "jdbc:postgresql://your-server-ip:port/your_database"
    private const val JDBC_USER = "your_username"
    private const val JDBC_PASSWORD = "your_password"

    init {
        // Разрешаем выполнение сетевых операций в главном потоке
        // Это нужно только для учебных целей. Не делайте этого в продакшене.
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
    }

    fun getConnection(): Connection? {
        return try {
            DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun executeQuery(query: String): ResultSet? {
        val connection = getConnection()
        return try {
            val statement: Statement = connection!!.createStatement()
            statement.executeQuery(query)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun closeConnection(connection: Connection?) {
        try {
            connection?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
