package com.example.aapki_awaaz.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aapki_awaaz.components.BottomNavItem
import com.example.aapki_awaaz.components.BottomNavigationBar
import com.example.aapki_awaaz.components.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row


@Composable
fun TrackScreen(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Unresolved (2)", "Resolved (1)")

    val complaints = remember {
        mutableStateListOf(
        ComplaintData(
            title = "Pothole on Main Street",
            authority = "City Municipal Corporation",
            likes = 45,
            warnings = 3,
            time = 2,
            following = 10,
            isResolved = false
        ),
        ComplaintData(
            title = "Street Light Not Working",
            authority = "Electricity Department",
            likes = 23,
            warnings = 2,
            time = 1,
            following = 5,
            isResolved = false
        )
    )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Track My Complaints",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Tab Row
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Complaint Cards based on unresolved or resolved tab
        LazyColumn {
            items(complaints.filter { it.isResolved == (selectedTabIndex == 1) }) { complaint ->
                ComplaintCard(
                    title = complaint.title,
                    authority = complaint.authority,
                    likes = complaint.likes,
                    warnings = complaint.warnings,
                    time = complaint.time,
                    following = complaint.following,
                    onResolveClick = {
                        val index = complaints.indexOf(complaint)
                        if(index != -1) {
                            complaints[index] = complaint.copy(isResolved = true)
                        }
                    },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
fun ComplaintCard(
    title: String,
    authority: String,
    likes: Int,
    warnings: Int,
    time: Int,
    following: Int,
    onResolveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        //elevation = 4.dp, // Correct usage of elevation as Dp
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Authority: $authority",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconWithText(text = "$likes", icon = Icons.Filled.ThumbUp)
                IconWithText(text = "$warnings", icon = Icons.Filled.Warning)
                IconWithText(text = "$time", icon = Icons.Filled.AccessTime)
                IconWithText(text = "Following($following)", icon = Icons.Filled.Notifications)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onResolveClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Resolve")
            }
        }
    }
}

@Composable
fun IconWithText(text: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Icon(imageVector = icon, contentDescription = text)
        Spacer(modifier = Modifier.padding(start = 4.dp))
        Text(text = text)
    }
}

data class ComplaintData(
    val title: String,
    val authority: String,
    val likes: Int,
    val warnings: Int,
    val time: Int,
    val following: Int,
    val isResolved: Boolean
)
