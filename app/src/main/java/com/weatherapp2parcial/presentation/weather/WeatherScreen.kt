package com.weatherapp2parcial.presentation.weather

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

@Composable
fun WeatherScreen(
    lat: Double,
    lon: Double,
    viewModel: WeatherViewModel = viewModel()
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
                state.weather?.let { data ->
                    Text("🌍 Ciudad: ${data.cityName}")
                    Text("🌡️ Temperatura: ${data.main.temp}°C")
                    Text("💧 Humedad: ${data.main.humidity}%")
                    Text("🌤️ Estado: ${data.weather.firstOrNull()?.description ?: "-"}")
                }
            }
        }
    }
}
