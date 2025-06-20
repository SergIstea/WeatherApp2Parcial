package com.weatherapp2parcial

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.weatherapp2parcial.presentation.cities.CitiesScreen
import com.weatherapp2parcial.presentation.weather.WeatherScreen

@Composable
fun WeatherApp() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "cities") {
        composable("cities") {
            CitiesScreen(
                onCitySelected = { city ->
                    navController.navigate("weather/${city.lat}/${city.lon}")
                }
            )
        }

        composable(
            route = "weather/{lat}/{lon}",
            arguments = listOf(
                navArgument("lat") { type = NavType.FloatType },
                navArgument("lon") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val lat = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 0.0
            val lon = backStackEntry.arguments?.getFloat("lon")?.toDouble() ?: 0.0
            WeatherScreen(lat = lat, lon = lon, navController = navController)
        }
    }
}
