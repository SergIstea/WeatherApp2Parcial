package com.weatherapp2parcial.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Forecast5DayResponse(
    val list: List<ForecastEntry>
)

@Serializable
data class ForecastEntry(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    @SerialName("dt_txt") val dtTxt: String
)


