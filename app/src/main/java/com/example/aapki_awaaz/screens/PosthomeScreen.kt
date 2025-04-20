package com.example.aapki_awaaz.screens

import kotlinx.serialization.Serializable

@Serializable
data class UserPost(
    val id: String,
    val image_url: String,
    val text: String,
    var like_count: Int,
    var severity_count: Int,
    var urgency_count: Int
)
