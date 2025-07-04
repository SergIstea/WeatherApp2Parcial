package com.weatherapp2parcial.presentation.cities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weatherapp2parcial.data.remote.CityDto
import com.weatherapp2parcial.presentation.cities.CitiesViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import com.weatherapp2parcial.data.local.UserPreferences
import kotlinx.coroutines.launch
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CitiesScreen(
    viewModel: CitiesViewModel = viewModel(),
    onCitySelected: (CityDto) -> Unit,
    onRequestLocation: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var query by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "🌦 La aplicación del clima",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        Button(
            onClick = { onRequestLocation() },
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        ) {
            Text("Mostrar ubicación")
        }
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.onIntent(CitiesIntent.SearchCity(query))
            },
            label = { Text("Buscar ciudad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text("Error: ${state.error}")
            }

            else -> {
                LazyColumn {
                    items(state.cities) { city ->
                        Text(
                            text = "${city.name}, ${city.country}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    coroutineScope.launch {
                                        UserPreferences(context).saveLocation(city.lat, city.lon)
                                        onCitySelected(city)
                                    }
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}
