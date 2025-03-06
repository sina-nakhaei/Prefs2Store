package ir.thesinaa.prefs2store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModelProvider
import ir.thesinaa.prefs2store.ui.theme.Prefs2StoreTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var  viewModel: SampleViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel = ViewModelProvider(this)[SampleViewModel::class.java]
        setContent {
            Prefs2StoreTheme {
                LaunchedEffect(Unit) {
                    val key = "username"
                    val value = "sina_nakhaei"
                    viewModel.saveToSharedPref(key, value)
                    delay(1000)
                    viewModel.migrate()
                    delay(1000)
                    viewModel.getFromDataStore(key)
                }
            }
        }
    }
}

const val TAG = "Pref2Store"