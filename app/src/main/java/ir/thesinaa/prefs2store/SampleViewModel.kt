package ir.thesinaa.prefs2store

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SampleViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPrefsHelper = SharedPrefHelper(application.applicationContext)
    private val dataStoreHelper = DataStoreHelper(application.applicationContext)

    fun saveToSharedPref(key: String, value: String) {
        Log.d(TAG, "saveToSharedPref | key: $key -- value: $value")
        sharedPrefsHelper.saveString(key, value)
    }

    fun migrate() {
        viewModelScope.launch {
            dataStoreHelper.migrate(sharedPrefsHelper.sharedPreferences)
        }
    }

    fun getFromDataStore(key: String) {
        viewModelScope.launch {
            dataStoreHelper
                .getString(key)
                .collect {
                    Log.d(TAG, "collecting from dataStore | key: $key -- value: $it")
                }
        }
    }
}