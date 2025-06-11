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

@Composable
fun CitiesScreen(
    viewModel: CitiesViewModel = viewModel(),
    onCitySelected: (CityDto) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
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
                                .clickable { onCitySelected(city) }
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}
