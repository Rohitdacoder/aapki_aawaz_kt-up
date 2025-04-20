/*package com.example.aapki_awaaz.St_City_fetch

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class ComplaintViewModel : ViewModel() {
    // States to hold suggestions
    var stateSuggestions = mutableStateOf<List<String>>(emptyList())
    var citySuggestions = mutableStateOf<List<String>>(emptyList())

    // Function to fetch states
    fun fetchStates(query: String) {
        // Query to fetch states matching the user's input
        val result = supabase.from("state")
            .select("name")
            .ilike("name", "%$query%")
            .execute()

        stateSuggestions.value = result.data?.map { it["name"] as String } ?: emptyList()
    }

    // Function to fetch cities based on selected state
    fun fetchCities(query: String, stateId: Int) {
        // Query to fetch cities matching the user's input and filtered by state_id
        val result = supabase.from("city")
            .select("name")
            .ilike("name", "%$query%")
            .eq("state_id", stateId)
            .execute()

        citySuggestions.value = result.data?.map { it["name"] as String } ?: emptyList()
    }
}*/
