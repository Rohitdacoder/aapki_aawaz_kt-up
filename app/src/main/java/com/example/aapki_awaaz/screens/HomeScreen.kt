package com.example.aapki_awaaz.screens

import android.R.attr.track
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.aapki_awaaz.bucket.UploadScreen
import com.example.aapki_awaaz.components.BottomNavItem
import com.example.aapki_awaaz.components.BottomNavigationBar
import com.example.aapki_awaaz.components.TabRow
import com.example.aapki_awaaz.components.TopAppBar
import com.example.aapki_awaaz.screens.TrackScreen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import coil.compose.AsyncImage
import com.example.aapki_awaaz.database.fetchPosts
import com.example.aapki_awaaz.database.updatePostField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var selectedItem by remember { mutableStateOf(BottomNavItem.Home as BottomNavItem) }
    val context = LocalContext.current
    Column(modifier = modifier.fillMaxSize()) {
        // Show TopAppBar and TabRow only for Home screen
        if (selectedItem is BottomNavItem.Home) {
            TopAppBar()
            TabRow()
        }

        // Conditional rendering based on selectedItem
        Column(modifier = Modifier
            .weight(1f)
            .padding(16.dp)) {
            when (selectedItem) {
                is BottomNavItem.Home -> {
                    HomePostsScreen()
                    // Your Home screen content (replace with your actual home content)
                    // Example content for Home

                }

                is BottomNavItem.Complaint -> {
                    ComplaintFormScreen()
                    // Show Complaint form when Complaint tab is selected ComplaintFormScreen()
                }

                is BottomNavItem.Track -> {
                    // Show Track screen when Track tab is selected
                    TrackComplaintsScreen()// Replace with the actual screen
                }

                is BottomNavItem.Profile -> {
                    // Show Profile screen when Profile tab is selected
                    //ProfileScreen()
                        ProfileScreen()
                }
            }
        }

        // Bottom navigation bar to switch between different tabs
        BottomNavigationBar(
            items = listOf(
                BottomNavItem.Home,
                BottomNavItem.Complaint,
                BottomNavItem.Track,
                BottomNavItem.Profile
            ),
            onItemClick = { selectedItem = it } // Update the state on item click
        )
    }
}

@Composable
fun HomePostsScreen() {
    var posts by remember { mutableStateOf<List<UserPost>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        posts = fetchPosts()
        isLoading = false
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        LazyColumn {
            items(posts) { post ->
                PostCard(post)
            }
        }
    }
}


@Composable
fun PostCard(post: UserPost) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = post.image_url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = post.text)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                InteractionButton("👍", post.like_count) {
                    post.like_count++
                    CoroutineScope(Dispatchers.IO).launch {
                        updatePostField(post.id, "like_count", post.like_count)
                    }
                }

                InteractionButton("⚠️", post.severity_count) {
                    post.severity_count++
                    CoroutineScope(Dispatchers.IO).launch {
                        updatePostField(post.id, "severity_count", post.severity_count)
                    }
                }

                InteractionButton("🚨", post.urgency_count) {
                    post.urgency_count++
                    CoroutineScope(Dispatchers.IO).launch {
                        updatePostField(post.id, "urgency_count", post.urgency_count)
                    }
                }
            }
        }
    }
}

@Composable
fun InteractionButton(emoji: String, count: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = "$emoji $count")
    }
}

