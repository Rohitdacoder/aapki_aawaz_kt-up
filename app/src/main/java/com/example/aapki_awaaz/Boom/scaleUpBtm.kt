package com.example.aapki_awaaz.Boom

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@Composable
fun AnimatedButton(
    text: String,
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()
    var isPressed by remember { mutableStateOf(false) }

    val normalColor = Color(0xFF2196F3) // Light blue
    val pressedColor = Color(0xFFAF15C0) // Darker blue

    Button(
        onClick = {
            scope.launch {
                // Change Color
                isPressed = true
                // Scale up
                scale.animateTo(
                    targetValue = 1.2f,
                    animationSpec = tween(durationMillis = 300)
                )
                // Scale back to normal
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 300)
                )

                // After animantion, reset Color
                isPressed = false
            }
            onClickAction()
        },
        modifier = Modifier
            .scale(scale.value)
            .padding(8.dp)
            .fillMaxWidth(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed) pressedColor else normalColor,
            contentColor = Color.White
        ),
        shape = ButtonDefaults.shape
    ) {
        Text(text)
    }
}
