package com.weatherapp2parcial.presentation.cities

sealed class CitiesEvent {
    object LoadCities : CitiesEvent()
    data class SearchCity(val query: String) : CitiesEvent()
}
