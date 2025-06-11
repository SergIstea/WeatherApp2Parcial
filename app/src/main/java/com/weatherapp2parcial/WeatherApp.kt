package com.weatherapp2parcial

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.weatherapp2parcial.presentation.cities.CitiesScreen

@Composable
fun WeatherApp() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "cities") {
        composable("cities") {
            CitiesScreen(
                onCitySelected = { city ->
                    // Por ahora solo mostramos la ciudad seleccionada en logs
                    println("Ciudad seleccionada: $city")
                }
            )
        }
    }
}
