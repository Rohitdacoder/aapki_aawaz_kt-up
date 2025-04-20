package com.example.aapki_awaaz.database

import com.example.aapki_awaaz.screens.UserPost
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.plugins.SupabasePluginProvider
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import io.ktor.client.plugins.auth.Auth

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://iapdsorocwpducvnmwxj.supabase.co",  // Replace with your actual URL
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlhcGRzb3JvY3dwZHVjdm5td3hqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQ4MTA3NTQsImV4cCI6MjA2MDM4Njc1NH0.VcRFIWeWdC6sO3C5BjFlEwFp8pYyIaTq1nRfYVoqUqU"  // Replace with your actual Supabase API Key

    ) {
        install(Postgrest)  // For interacting with the database (PostgREST)
        //install(storage)    // For interacting with file storage
        //install(Auth)
        install(Realtime)
        install(Storage)
    }

}
