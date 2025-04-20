package com.example.aapki_awaaz.database

import com.example.aapki_awaaz.screens.UserPost  // 🔥 Use your renamed Post model
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.from

suspend fun fetchPosts(): List<UserPost> {
    return SupabaseClient.client.from("posts")
        .select()
        .decodeList<UserPost>()
}

suspend fun updatePostField(postId: String, field: String, newValue: Int) {
    SupabaseClient.client.from("posts").update(
        {
            set(field, newValue)
        }
    ) {
        filter {
            eq("id", postId)
        }
    }
}
