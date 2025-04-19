
/*package com.example.aapki_awaaz.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

data class Complaint(
    val title: String,
    val description: String,
    val status: ComplaintStatus
)

enum class ComplaintStatus {
    Pending, InProgress, Resolved
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TrackComplaintsScreen() {
    val tabs = listOf("Pending", "In Progress", "Resolved")
    var selectedTabIndex by remember { mutableStateOf(0) }

    val complaints = listOf(
        Complaint("Login Issue", "Unable to login to account", ComplaintStatus.Pending),
        Complaint("Slow Performance", "App is running slow", ComplaintStatus.InProgress),
        Complaint("Payment Failed", "Payment not successful", ComplaintStatus.Resolved),
        Complaint("Bug in Profile", "Profile picture not updating", ComplaintStatus.Pending),
        Complaint("Crash on Launch", "App crashes at startup", ComplaintStatus.InProgress)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Track Complaints",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0D47A1)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Navigate to New Complaint Screen */ },
                containerColor = Color(0xFF0D47A1),
                shape = RoundedCornerShape(50)
            ) {
                Text("+", fontSize = 30.sp, color = Color.White)
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                contentColor = Color(0xFF0D47A1),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .height(3.dp),
                        color = Color(0xFF0D47A1)
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                title,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val filteredComplaints = when (selectedTabIndex) {
                0 -> complaints.filter { it.status == ComplaintStatus.Pending }
                1 -> complaints.filter { it.status == ComplaintStatus.InProgress }
                2 -> complaints.filter { it.status == ComplaintStatus.Resolved }
                else -> complaints
            }

            AnimatedContent(
                targetState = filteredComplaints,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) with fadeOut(animationSpec = tween(300))
                }
            ) { complaintsToShow ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(complaintsToShow) { complaint ->
                        ComplaintItem(complaint)
                    }
                }
            }
        }
    }
}

@Composable
fun ComplaintItem(complaint: Complaint) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { /* TODO: View details */ },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                complaint.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                complaint.description,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = complaint.status.name.replaceFirstChar { it.uppercase() },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = when (complaint.status) {
                        ComplaintStatus.Pending -> Color.Red
                        ComplaintStatus.InProgress -> Color(0xFFFF9800)
                        ComplaintStatus.Resolved -> Color(0xFF4CAF50)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrackComplaintsScreenPreview() {
    TrackComplaintsScreen()
}*/
package com.example.aapki_awaaz.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

data class Complaint(
    val title: String,
    val description: String,
    val status: ComplaintStatus
)

enum class ComplaintStatus {
    Pending, InProgress, Resolved
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TrackComplaintsScreen() {
    val tabs = listOf("Pending", "In Progress", "Resolved")
    var selectedTabIndex by remember { mutableStateOf(0) }

    val complaints = listOf(
        Complaint("Login Issue", "Unable to login to account", ComplaintStatus.Pending),
        Complaint("Slow Performance", "App is running slow", ComplaintStatus.InProgress),
        Complaint("Payment Failed", "Payment not successful", ComplaintStatus.Resolved),
        Complaint("Bug in Profile", "Profile picture not updating", ComplaintStatus.Pending),
        Complaint("Crash on Launch", "App crashes at startup", ComplaintStatus.InProgress)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Track Complaints",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0D47A1)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = Color(0xFF0D47A1),
                shape = RoundedCornerShape(50)
            ) {
                Text("+", fontSize = 30.sp, color = Color.White)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                contentColor = Color(0xFF0D47A1),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .height(3.dp),
                        color = Color(0xFF0D47A1)
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                title,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val filteredComplaints = when (selectedTabIndex) {
                0 -> complaints.filter { it.status == ComplaintStatus.Pending }
                1 -> complaints.filter { it.status == ComplaintStatus.InProgress }
                2 -> complaints.filter { it.status == ComplaintStatus.Resolved }
                else -> complaints
            }

            AnimatedContent(
                targetState = filteredComplaints,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) with fadeOut(animationSpec = tween(300))
                }
            ) { complaintsToShow ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(complaintsToShow) { complaint ->
                        ComplaintItem(complaint)
                    }
                }
            }
        }
    }
}

@Composable
fun ComplaintItem(complaint: Complaint) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { /* TODO */ },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = complaint.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = complaint.description,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                StatusChip(status = complaint.status)
            }
        }
    }
}

@Composable
fun StatusChip(status: ComplaintStatus) {
    Surface(
        color = when (status) {
            ComplaintStatus.Pending -> Color(0xFFFFCDD2)
            ComplaintStatus.InProgress -> Color(0xFFFFE0B2)
            ComplaintStatus.Resolved -> Color(0xFFC8E6C9)
        },
        shape = RoundedCornerShape(50),
        tonalElevation,
        modifier = TODO(),
        contentColor = TODO(),
        tonalElevation = TODO(),
        shadowElevation = TODO(),
        border = TODO(),
        content = TODO()

