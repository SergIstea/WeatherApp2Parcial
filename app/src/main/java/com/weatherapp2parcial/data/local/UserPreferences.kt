package com.weatherapp2parcial.data.local

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPreferencesKeys {
    val LAT = doublePreferencesKey("lat")
    val LON = doublePreferencesKey("lon")
}

class UserPreferences(private val context: Context) {

    suspend fun saveLocation(lat: Double, lon: Double) {
        context.dataStore.edit { prefs ->
            prefs[UserPreferencesKeys.LAT] = lat
            prefs[UserPreferencesKeys.LON] = lon
        }
    }

    suspend fun getLocation(): Pair<Double, Double>? {
        return context.dataStore.data.map { prefs ->
            val lat = prefs[UserPreferencesKeys.LAT]
            val lon = prefs[UserPreferencesKeys.LON]
            if (lat != null && lon != null) Pair(lat, lon) else null
        }.first()
    }

    suspend fun clearLocation() {
        context.dataStore.edit { preferences ->
            preferences.remove(UserPreferencesKeys.LAT)
            preferences.remove(UserPreferencesKeys.LON)
        }
    }
}
