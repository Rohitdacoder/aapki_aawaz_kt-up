package com.example.aapki_awaaz.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TabRow(modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(16.dp)) {
        Button(
            onClick = { /* Handle Trending tab click */ },
            modifier = Modifier.padding(end = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            shape = RoundedCornerShape(10.dp)

        ) {
            Text("Trending", color = Color.White)
        }
        Button(
            onClick = { /* Handle Recently Resolved tab click */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Recently Resolved")
        }
    }
}