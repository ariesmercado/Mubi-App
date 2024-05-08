package com.haroldcalayan.mubi.data.source.local.pref

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(private val context: Context, private val sharedPrefName: String) {

    fun pref(): SharedPreferences = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)

    companion object {

        inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
            val editor = this.edit()
            operation(editor)
            editor.apply()
        }

        /**
         * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
         */
        operator fun SharedPreferences.set(key: String, value: Any?) {
            when (value) {
                is String? -> edit { it.putString(key, value) }
                is Int -> edit { it.putInt(key, value) }
                is Boolean -> edit { it.putBoolean(key, value) }
                is Double -> edit { it.putLong(key, value.toRawBits()) }
                is Float -> edit { it.putFloat(key, value) }
                is Long -> edit { it.putLong(key, value) }
                else -> throw UnsupportedOperationException("Not yet implemented")
            }
        }

        /**
         * finds value on given key.
         * [T] is the type of value
         * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
         */
        inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
            return when (T::class) {
                String::class -> getString(key, defaultValue as? String) as T?
                Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
                Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
                Double::class -> {
                    var value = Double.fromBits(getLong(key, defaultValue as? Long ?: -1))
                    if (value.isNaN()) value = 0.0
                    value as T
                }
                Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
                Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
                else -> throw UnsupportedOperationException("Not yet implemented")
            }
        }
    }
}