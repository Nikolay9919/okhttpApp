package com.nikolay.okhttpapp.SQLite

import android.provider.BaseColumns
import com.nikolay.okhttpapp.SQLite.FeedReaderContract.FeedEntry.TABLE_URL

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

    /*const val SQL_CREATE_TRIGGER_MAX_VALUE = "CREATE TRIGGER URl_row_count" +
            "BEFORE INSERT ON $TABLE_URL " +
            "WHEN (SELECT COUNT(*) FROM $TABLE_URL) >= 3    " +
            " BEGIN " +
            " SELECT RAISE(FAIL, 'too many rows');" +
            "END;"*/
}