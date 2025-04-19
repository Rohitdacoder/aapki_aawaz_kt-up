import com.example.aapki_awaaz.database.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*package com.example.aapki_awaaz.database

import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

// Fetching states from the 'states' table
suspend fun fetchStates(): List<String> {
    return withContext(Dispatchers.IO) {
        try {
            // Fetch the data from the 'states' table
            val response = SupabaseClient.client.postgrest
                .from("states")  // Specify the table name
                .select("name")  // Fetch the 'name' column from the table
                .execute()

            // Check if the response is successful
            if (response.isSuccessful) {
                // Deserialize the response body into a list of states
                val responseBody = response.body<String>() // This should be a JSON array in string format
                val states = Json.decodeFromString<List<Map<String, String>>>(responseBody)

                // Extract state names from the response
                states.map { it["name"] ?: "" } // Extract 'name' key
            } else {
                // Return an empty list if the request failed
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Return an empty list in case of any error
            emptyList()
        }
    }
}*/// Data class to represent a state
data class State(val name: String)

// Fetching states from the 'states' table
suspend fun fetchStates(): List<String> {
    return withContext(Dispatchers.IO) {
        try {
            // Fetch the data from the 'states' table
            val response = SupabaseClient.client.postgrest["states"].select {
                     "name"
            }

            // Deserialize the response body into a list of State objects
            val states = response.decodeList<State>()

            // Extract state names from the response
            states.map { it.name } // Extract 'name'
        } catch (e: Exception) {
            e.printStackTrace()
            // Return an empty list in case of any error
            emptyList()
        }
    }
}

// Data class to represent a city
data class City(val name: String)

// Fetching cities from the 'cities' table
suspend fun fetchCities(): List<String> {
    return withContext(Dispatchers.IO) {
        try {
            // Fetch the data from the 'cities' table
            val response = SupabaseClient.client.postgrest["cities"].select {
                "name"
            }

            // Deserialize the response body into a list of City objects
            val cities = response.decodeList<City>()

            // Extract city names from the response
            cities.map { it.name } // Extract 'name'
        } catch (e: Exception) {
            e.printStackTrace()
            // Return an empty list in case of any error
            emptyList()
        }
    }
}






