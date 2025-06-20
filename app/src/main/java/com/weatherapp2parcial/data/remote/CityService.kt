    package com.weatherapp2parcial.data.remote


    import io.ktor.client.call.body
    import io.ktor.client.request.get
    import io.ktor.client.request.parameter
    import io.ktor.client.statement.HttpResponse
    import io.ktor.client.statement.bodyAsText

    class CityService {

        private val apiKey = "709fd2926abca39b7bf90ceb838c9be3"

        suspend fun getCityByName(city: String): List<CityDto> {
            val response: HttpResponse =
                httpClient.get("https://api.openweathermap.org/geo/1.0/direct") {
                    parameter("q", city)
                    parameter("limit", 1)
                    parameter("appid", apiKey)
                }
            val cityList: List<CityDto> = response.body()
            cityList.firstOrNull()?.let {
                println("🛰 Coordenadas obtenidas: lat=${it.lat}, lon=${it.lon}")
            }

            return cityList
        }
        suspend fun getCurrentWeatherByCoordinates(lat: Double, lon: Double): CurrentWeatherDto {
            val response: HttpResponse = httpClient.get("https://api.openweathermap.org/data/2.5/weather") {
                parameter("lat", lat)
                parameter("lon", lon)
                parameter("appid", apiKey)
                parameter("units", "metric") // para mostrar temperatura en °C
                parameter("lang", "es") // para que la descripción venga en español (opcional)
            }
            return response.body()
        }

        suspend fun get5DayForecast(lat: Double, lon: Double): Forecast5DayResponse {
            val response: HttpResponse = httpClient.get("https://api.openweathermap.org/data/2.5/forecast") {
                parameter("lat", lat)
                parameter("lon", lon)
                parameter("appid", apiKey)
                parameter("units", "metric")
                parameter("lang", "es")
            }
            println("📦 Forecast recibido: ${response.bodyAsText()}")
            return response.body()
        }

    }