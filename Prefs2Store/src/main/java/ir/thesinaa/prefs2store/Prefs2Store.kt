package ir.thesinaa.prefs2store

import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class Prefs2Store(
    private val sharedPrefs: SharedPreferences,
    private val dataStore: DataStore<Preferences>
) {
    suspend fun migrate(
        excludeKeys: Set<String> = emptySet(),
        clearOnSuccess: Boolean = true,
        onError: (key: String, exception: Exception) -> Unit = { key, e ->
            Log.e(
                "Migrator",
                "Failed $key: $e"
            )
        },
    ) {
        dataStore.edit { prefs ->
            prefs.clear()

            sharedPrefs.all.forEach { (key, value) ->
                if (key !in excludeKeys) {
                    try {
                        when (value) {
                            is String -> prefs[stringPreferencesKey(key)] = value
                            is Int -> prefs[intPreferencesKey(key)] = value
                            is Boolean -> prefs[booleanPreferencesKey(key)] = value
                            is Float -> prefs[floatPreferencesKey(key)] = value
                            is Long -> prefs[longPreferencesKey(key)] = value
                            is Set<*> -> prefs[stringSetPreferencesKey(key)] = value as Set<String>
                            else -> throw IllegalArgumentException("Unsupported type for $key")
                        }
                    } catch (e: Exception) {
                        onError(key, e)
                    }
                }
            }
            prefs[booleanPreferencesKey("migration_complete")] = true
        }
        if (clearOnSuccess)
            sharedPrefs.edit().clear().apply()
    }

    suspend fun isMigrated() =
        dataStore
            .data
            .map { it[booleanPreferencesKey("migration_complete")] ?: false }
            .first()
}