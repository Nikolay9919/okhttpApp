package com.nikolay.okhttpapp.Helpers

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log


class SharedPreferencesHelper {
    private val FILE_NAME = "URL"

    @SuppressLint("CommitPrefEdits")
    fun put(context: Context, key: String, value: String) {
        val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(key, value)
        editor.apply()
    }

    operator fun get(context: Context, key: String): String? {

        val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        Log.d("spGet", sp.all.toString())
        return sp.getString(key, null)

    }

    fun remove(context: Context, key: String) {
        val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key)
        editor.apply()
    }
}
