package com.ferdyrodriguez.data.local

import android.content.SharedPreferences

class PreferenceHelper constructor(val sharedPrefs: SharedPreferences) {

    fun clearAll() = sharedPrefs.edit().clear().apply()

    fun contains(key: String) = sharedPrefs.contains(key)

    inline fun <reified T> getPreference(key: String, defValue: T? = null): T? = with(sharedPrefs) {
        return when (T::class) {
            Long::class -> getLong(key, defValue as? Long ?: -1) as T?
            String::class -> getString(key, defValue as? String ?: "") as T?
            Int::class -> getInt(key, defValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defValue as? Float ?: -1f) as T?
            else -> throw IllegalArgumentException("This type can´t be found and saved into Preferences")
        }
    }

    fun setPreference(key: String, value: Any) = with(sharedPrefs) {
        when (value) {
            is Long -> edit { it.putLong(key, value) }
            is String -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
    }

    private inline fun SharedPreferences.edit(action: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        action(editor)
        editor.apply()
    }
}