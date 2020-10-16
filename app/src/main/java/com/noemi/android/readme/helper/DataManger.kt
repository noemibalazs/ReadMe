package com.noemi.android.readme.helper

import android.content.Context
import com.noemi.android.readme.util.MY_PREFERENCE
import com.noemi.android.readme.util.REPOSITORY_FULL_NAME
import com.noemi.android.readme.util.REPOSITORY_ID

class DataManger(private val context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE)

    fun saveRepositoryId(id: Int) {
        sharedPreferences.edit().putInt(REPOSITORY_ID, id).apply()
    }

    fun getRepositoryId(): Int {
        return sharedPreferences.getInt(REPOSITORY_ID, 0)
    }

    fun saveRepositoryFullName(fullName: String) {
        sharedPreferences.edit().putString(REPOSITORY_FULL_NAME, fullName).apply()
    }

    fun getRepositoryFullName(): String {
        return sharedPreferences.getString(REPOSITORY_FULL_NAME, "") ?: ""
    }
}