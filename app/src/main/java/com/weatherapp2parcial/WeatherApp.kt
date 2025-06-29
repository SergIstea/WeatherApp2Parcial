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
    import com.google.android.gms.location.LocationServices
    import android.annotation.SuppressLint
    import android.content.Context
    import android.util.Log
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.runtime.remember

    @SuppressLint("MissingPermission")
    @Composable
    fun WeatherApp(savedLocation: Pair<Double, Double>?) {

        val navController: NavHostController = rememberNavController()
        val context = LocalContext.current

        val fusedLocationClient = remember {
            LocationServices.getFusedLocationProviderClient(context)        }
        val startDestination = if (savedLocation != null) {
            "weather/${savedLocation.first}/${savedLocation.second}"
        } else {
            "cities"
        }

        NavHost(navController = navController, startDestination = startDestination) {
            composable("cities") {
                CitiesScreen(
                    onCitySelected = { city ->
                        navController.navigate("weather/${city.lat}/${city.lon}")
                    },onRequestLocation = {
                        Log.d("WeatherApp", "Botón de ubicación presionado")
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location ->
                                if (location != null) {
                                    Log.d("WeatherApp", "Ubicación obtenida: ${location.latitude}, ${location.longitude}")
                                    navController.navigate("weather/${location.latitude}/${location.longitude}")
                                } else {
                                    Log.e("WeatherApp", "No se pudo obtener la ubicación (es null)")
                                    val defaultLat = -34.5235766
                                    val defaultLon = -58.5294344
                                    navController.navigate("weather/$defaultLat/$defaultLon")
                                }
                            }
                            .addOnFailureListener {
                                Log.e("WeatherApp", "Error al obtener la ubicación: ${it.message}")
                            }
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
