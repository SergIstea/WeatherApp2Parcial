package com.weatherapp2parcial.presentation.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp2parcial.data.remote.CityService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CitiesViewModel : ViewModel() {

    private val _state = MutableStateFlow(CitiesState())
    val state: StateFlow<CitiesState> = _state

    fun onIntent(intent: CitiesIntent) {
        when (intent) {
            is CitiesIntent.SearchCity -> {
                searchCity(intent.query)
            }
        }
    }

    private fun searchCity(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val cities = CityService().getCityByName(query)
                _state.update { it.copy(cities = cities, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
            }
        }
    }
}
