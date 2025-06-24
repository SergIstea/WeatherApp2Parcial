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
                getCurrentWeather(intent.lat, intent.lon)
                getForecast(intent.lat, intent.lon)
            }
            is WeatherIntent.LoadForecast -> {
                getForecast(intent.lat, intent.lon)
            }
        }
    }

    private fun getCurrentWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val result = service.getCurrentWeatherByCoordinates(lat, lon)
                _state.value = _state.value.copy(weather = result, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }
    private fun getForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val result = service.get5DayForecast(lat, lon)
                println("🧩 Forecast recibido: ${result.list.size} entradas")
                _state.value = _state.value.copy(forecast = result.list)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

}