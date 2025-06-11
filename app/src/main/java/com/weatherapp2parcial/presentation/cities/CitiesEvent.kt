package com.weatherapp2parcial.presentation.cities
import com.weatherapp2parcial.data.remote.CityDto

sealed class CitiesEvent {
    data class ShowCities(val cities: List<CityDto>) : CitiesEvent()
    data class ShowError(val message: String) : CitiesEvent()
}
