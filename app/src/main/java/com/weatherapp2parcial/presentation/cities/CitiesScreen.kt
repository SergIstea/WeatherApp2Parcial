package com.weatherapp2parcial.presentation.cities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CitiesScreen(
    viewModel: CitiesViewModel = viewModel(),
    onCitySelected: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var search by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        viewModel.onEvent(CitiesEvent.LoadCities)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = search,
            onValueChange = {
                search = it
                viewModel.onEvent(CitiesEvent.SearchCity(it))
            },
            label = { Text("Buscar ciudad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(state.filteredCities) { city ->
                Text(
                    text = city,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCitySelected(city) }
                        .padding(12.dp)
                )
            }
        }
    }
}
