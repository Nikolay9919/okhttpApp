package com.nikolay.okhttpapp

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast


class SharedPreferencesHelper {
    private val FILE_NAME = "URL"

    @SuppressLint("CommitPrefEdits")
    fun put(context: Context, key: String, value: String) {
        val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()

        editor.putString(key, value)
        editor.apply()
    }

    operator fun get(context: Context): ArrayList<String> {

        val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val map = sp.all as Map<String, String>
        val list = ArrayList<String>()
        for (entry in map.entries) {


            val savedPref = sp.getString(entry.key, "")
            list += savedPref
            Toast.makeText(context, savedPref, Toast.LENGTH_LONG).show()
        }
        return list

    }

    fun remove(context: Context, key: String) {
        val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key)
        editor.apply()
    }
}
