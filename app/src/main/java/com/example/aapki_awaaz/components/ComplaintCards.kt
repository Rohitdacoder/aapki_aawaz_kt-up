package com.example.aapki_awaaz.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aapki_awaaz.R

@Composable
fun ComplaintCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Bank Scam, no help from Police", fontWeight = FontWeight.Bold)
                Text(text = "Bareilly, Uttar Pradesh", fontSize = 12.sp)
            }
            IconButton(onClick = { /* Handle more options */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.user_avatar),//baseline_more_vert_24
                    contentDescription = "More"
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.user_avatar), // Replace with actual image resource and  bank_image
            contentDescription = "Complaint Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Waiting for the approved loan for irrigation, I'm visiting the bank manager again and again...", fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Filled.ThumbUp, contentDescription = "Like", tint = Color.White, modifier = Modifier.background(Color(0xFF9C27B0), RoundedCornerShape(5.dp)).padding(5.dp))
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "345")
            Spacer(modifier = Modifier.width(16.dp))
            Icon(painter = painterResource(id = R.drawable.user_avatar) /*baseline_comment_24*/,contentDescription = "Comment", tint = Color.White, modifier = Modifier.background(Color(0xFF9C27B0), RoundedCornerShape(5.dp)).padding(5.dp))
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "257")
            Spacer(modifier = Modifier.width(16.dp))
            Icon(painter = painterResource(id = R.drawable.user_avatar)/*baseline_priority_high_24*/,contentDescription = "Exclamation", tint = Color.White, modifier = Modifier.background(Color(0xFF9C27B0), RoundedCornerShape(5.dp)).padding(5.dp))
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "824")
        }
    }
}