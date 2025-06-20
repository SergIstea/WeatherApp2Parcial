package com.weatherapp2parcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.weatherapp2parcial.data.local.UserPreferences
import com.weatherapp2parcial.presentation.ui.theme.WeatherApp2ParcialTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
        val userPreferences = UserPreferences(applicationContext)
        val savedLocation = userPreferences.getLocation()

        setContent {
            WeatherApp2ParcialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherApp(savedLocation)
                    }
                }
            }
        }
    }
}
