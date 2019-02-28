package com.nikolay.okhttpapp.SQLite

import android.provider.BaseColumns

object FeedReaderContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val DbName = "url.db"
        const val DbVersion = 1
        const val TABLE_URL = "url"
        const val COLUMN_URL = "url"
    }


    const val SQL_CREATE_ENTRIES_URL =
        "CREATE TABLE ${FeedEntry.TABLE_URL} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${FeedEntry.COLUMN_URL} TEXT NOT NULL );"

    const val SQL_INSERT_INTO_TABLE =
        "INSERT INTO ${FeedEntry.TABLE_URL} ( " +
                "${FeedEntry.COLUMN_URL} )" +
                " VALUES ('https://api.github.com/repos/apple/swift/events');"
    /*const val SQL_CREATE_TRIGGER_MAX_VALUE = "CREATE TRIGGER URl_row_count" +
            "BEFORE INSERT ON $TABLE_URL " +
            "WHEN (SELECT COUNT(*) FROM $TABLE_URL) >= 3    " +
            " BEGIN " +
            " SELECT RAISE(FAIL, 'too many rows');" +
            "END;"*/
}