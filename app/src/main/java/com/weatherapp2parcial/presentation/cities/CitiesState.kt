package com.weatherapp2parcial.presentation.cities

data class CitiesState(
    val allCities: List<String> = emptyList(),
    val filteredCities: List<String> = emptyList()
)
