package com.weatherapp2parcial.presentation.cities

sealed class CitiesIntent {
    data class SearchCity(val query: String) : CitiesIntent()
}