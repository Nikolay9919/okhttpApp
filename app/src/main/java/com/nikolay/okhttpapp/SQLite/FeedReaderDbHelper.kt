package com.nikolay.okhttpapp.SQLite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class FeedReaderDbHelper(context: Context) :
    SQLiteOpenHelper(context, FeedReaderContract.FeedEntry.DbName, null, FeedReaderContract.FeedEntry.DbVersion) {
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(FeedReaderContract.SQL_CREATE_ENTRIES_URL)
        db?.execSQL(FeedReaderContract.SQL_INSERT_INTO_TABLE)


    }

    fun addTask(url: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_URL, url)
        }
        Log.d("addUrl:", values.toString())
        val newRow = db.insert(FeedReaderContract.FeedEntry.TABLE_URL, null, values)
        db.close()
    }

    @SuppressLint("Recycle")
    fun getAllTasks(): ArrayList<String>? {
        val db = readableDatabase

        val selectQuery: String = "SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE_URL
        val cursor = db.rawQuery(selectQuery, null)
        val urlList = ArrayList<String>()
        if (cursor.moveToFirst())
            urlList.add(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_URL)))
        db.close()
        Log.d("urlList:", urlList.toString())
        return urlList
    }

}