package com.example.aapki_awaaz.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aapki_awaaz.ui.theme.lightBlue



sealed class BottomNavItem(val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("Home", Icons.Filled.Home)
    object Complaint : BottomNavItem("Complaint", Icons.Filled.Add)
    object Track : BottomNavItem("Track", Icons.Filled.Search)
    object Profile : BottomNavItem("Profile", Icons.Filled.Person)
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit // New parameter: onItemClick
) {
    val selectedItem = remember { mutableStateOf(items.first()) } // Track the first item as selected by default

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .background(Color.White)
            .padding(8.dp)
    ) {
        items.forEach { item ->
            BottomNavItem(
                item = item,
                isSelected = item == selectedItem.value,
                onClick = {
                    selectedItem.value = item // Update the selected item
                    onItemClick(item) // Call onItemClick with the clicked item
                } // Call onItemClick with the clicked item
            )
        }
    }
}

@Composable
fun BottomNavItem(
    item: BottomNavItem,
    onClick: () -> Unit,
    isSelected: Boolean // New parameter: isSelected
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable { onClick() } // Call onClick when clicked
            .background(
                if (isSelected) lightBlue else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(text = item.title, textAlign = TextAlign.Center, fontSize = 10.sp)
    }
}