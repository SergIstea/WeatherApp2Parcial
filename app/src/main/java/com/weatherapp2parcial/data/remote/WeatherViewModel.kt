package com.weatherapp2parcial.data.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp2parcial.data.remote.CityService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state

    private val service = CityService()

    fun onIntent(intent: WeatherIntent) {
        when (intent) {
            is WeatherIntent.LoadWeather -> {
                loadWeather(intent.lat, intent.lon)
            }
        }
    }

    private fun loadWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val weather = service.getCurrentWeatherByCoordinates(lat, lon)
                _state.update { it.copy(weather = weather, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message ?: "Error al cargar el clima", isLoading = false) }
            }
        }
    }
}