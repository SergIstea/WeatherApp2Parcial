package com.weatherapp2parcial.presentation.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weatherapp2parcial.data.remote.WeatherIntent
import com.weatherapp2parcial.data.remote.WeatherViewModel
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.navigation.NavController



@Composable
fun WeatherScreen(
    lat: Double,
    lon: Double,
    viewModel: WeatherViewModel = viewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()

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
                    onClick = { navController.navigate("cities") },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("🌍 Cambiar ciudad")
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
