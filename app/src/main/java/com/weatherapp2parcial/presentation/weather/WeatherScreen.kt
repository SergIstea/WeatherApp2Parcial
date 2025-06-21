package com.weatherapp2parcial.presentation.weather

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.weatherapp2parcial.data.local.UserPreferences
import com.weatherapp2parcial.data.remote.WeatherIntent
import com.weatherapp2parcial.data.remote.WeatherViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun WeatherScreen(
    lat: Double,
    lon: Double,
    viewModel: WeatherViewModel = viewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Al iniciar, dispara la intención de carga
    LaunchedEffect(Unit) {
        viewModel.onIntent(WeatherIntent.LoadWeather(lat, lon))
    }

    Column(modifier = Modifier.padding(16.dp)) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text("Error: ${state.error}")
            }

            else -> {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            UserPreferences(context).clearLocation()
                            navController.navigate("cities") {
                                popUpTo("weather/{lat}/{lon}") { inclusive = true }
                            }
                        }
                    },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("🔁 Cambiar ciudad")
                }
                Button(
                    onClick = {
                        val weather = state.weather?.weather?.firstOrNull()?.description ?: "-"
                        val shareText = """
            🌍 Ciudad: ${state.weather?.cityName}
            🌡️ Temperatura: ${state.weather?.main?.temp}°C
            💧 Humedad: ${state.weather?.main?.humidity}%
            🌤️ Estado: $weather
        """.trimIndent()

                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }

                        context.startActivity(Intent.createChooser(intent, "Compartir pronóstico con..."))
                    },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("📤 Compartir pronóstico")
                }
                state.weather?.let { data ->
                    Text("🌍 Ciudad: ${data.cityName}")
                    Text("🌡️ Temperatura: ${data.main.temp}°C")
                    Text("💧 Humedad: ${data.main.humidity}%")
                    Text("🌤️ Estado: ${data.weather.firstOrNull()?.description ?: "-"}")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("📅 Pronóstico de 5 días:", style = MaterialTheme.typography.titleMedium)

                LazyColumn {
                    items(state.forecast) { day ->
                        val date = SimpleDateFormat("EEE dd/MM", Locale.getDefault())
                            .format(Date(day.dt * 1000)) // timestamp en segundos

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("📆 $date")
                            Text("🌡️ Temp: ${day.main.temp.toInt()}°")
                            Text("☁️ ${day.weather.firstOrNull()?.description ?: "-"}")
                        }
                    }
                }
            }
        }
    }
}
