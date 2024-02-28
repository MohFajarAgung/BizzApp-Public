package com.thegreatdawn.bizzapp

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore by preferencesDataStore(name = "settings")
class SettingsManager(context: Context) {


    private val prefs: SharedPreferences = context.getSharedPreferences("business_pref", Context.MODE_PRIVATE)

    var businessIsStarted: Boolean
        get() = prefs.getBoolean(IS_STARTED_KEY, false)
        set(value) {
            prefs.edit().putBoolean(IS_STARTED_KEY , value).apply()
        }



    companion object {
        private const val IS_STARTED_KEY = "is_started"
    }
}