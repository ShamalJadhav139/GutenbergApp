package com.etpl.newbase.utilities

import android.content.Context
import android.content.SharedPreferences
import com.etpl.newbase.constants.APP_NAME
import com.google.gson.Gson

internal object AppSettingsCore{

    private const val SHARED_PREFERENCES_NAME = APP_NAME

    private val preferences: SharedPreferences?
        get() {
            try {
                if (AppDetails.context != null)
                    return AppDetails.context!!.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

     private val editor: SharedPreferences.Editor
        get() = preferences!!.edit()

    fun setBoolean(keyValue: String, value: Boolean) {
        val editor = editor
        editor.putBoolean(keyValue, value)
        editor.commit()
    }



    fun getBoolean(keyValue: String, defaultValue: Boolean): Boolean {
        val sharedPreferences = preferences
        return sharedPreferences!!.getBoolean(keyValue, defaultValue)
    }

    internal fun getBoolean(keyValue: String): Boolean {
        val sharedPreferences = preferences
        return sharedPreferences!!.getBoolean(keyValue, false)
    }

    fun setString(keyValue: String, value: String) {
        val editor = editor
        editor.putString(keyValue, value)
        editor.commit()
    }

    fun getString(key: String, defValue: String): String? {
        val pref = preferences
        return pref!!.getString(key, defValue)
    }

    fun setInt(keyValue: String, value: Int) {
        val editor = editor
        editor.putInt(keyValue, value)
        editor.commit()
    }

    fun getInt(keyValue: String, defValue: Int): Int {
        val pref = preferences
        return pref!!.getInt(keyValue, defValue)
    }

    fun removePrefData() {
        val editor = editor
        editor.clear()
        editor.commit()
    }
}
