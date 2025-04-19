package com.example.aapki_awaaz.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aapki_awaaz.R

@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Person, // Replace with your actual icon
            contentDescription = "App Icon",
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "AAWAZ",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.weight(1f)) // Push the icons to the right
        Icon(
            imageVector = Icons.Filled.Notifications,
            contentDescription = "Notifications",
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.Filled.QuestionMark,
            contentDescription = "Help",
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "Profile",
        )
    }
}