package com.weatherapp2parcial.presentation.cities

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CitiesViewModel : ViewModel() {

    private val _state = MutableStateFlow(CitiesState())
    val state: StateFlow<CitiesState> = _state.asStateFlow()

    fun onEvent(event: CitiesEvent) {
        when (event) {
            is CitiesEvent.SearchCity -> {
                val filtered = _state.value.allCities.filter {
                    it.contains(event.query, ignoreCase = true)
                }
                _state.value = _state.value.copy(filteredCities = filtered)
            }
            is CitiesEvent.LoadCities -> {
                val cities = listOf("Buenos Aires", "Córdoba", "Rosario", "Mendoza", "Salta")
                _state.value = _state.value.copy(allCities = cities, filteredCities = cities)
            }
        }
    }
}
