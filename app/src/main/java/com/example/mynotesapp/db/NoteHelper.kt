package com.example.mynotesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.mynotesapp.db.DatabaseContract.NoteColums.Companion.TABLE_NAME
import com.example.mynotesapp.db.DatabaseContract.NoteColums.Companion._ID
import java.sql.SQLException


class NoteHelper(context: Context) {
    private val databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: NoteHelper(context)
        }

    }


    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        database.close()
        if (database.isOpen) {
            database.close()
        }
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )

    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$id ASC",
            arrayOf(id),
            null,
            null,
            null
        )
    }
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE,null,values)

    }
    fun update(id: String, values: ContentValues?): Int{
        return database.update(DATABASE_TABLE,values,"$_ID = ?", arrayOf(id))
    }
    fun delete(id:String): Int {
        return database.delete(DATABASE_TABLE,"$_ID = $id", null)
    }


}