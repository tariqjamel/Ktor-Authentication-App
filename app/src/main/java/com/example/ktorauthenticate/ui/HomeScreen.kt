package com.example.ktorauthenticate.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val rotationAnim = rememberInfiniteTransition()
                val rotation by rotationAnim.animateFloat(
                    initialValue = 0f,
                    targetValue = 720f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(3000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )

                val bodyAnimation = rememberInfiniteTransition()
                val bodyRotation by bodyAnimation.animateFloat(
                    initialValue = -90f,
                    targetValue = -225f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(3000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                // Animation for scaling the shopping cart icon
                val scaleAnim = rememberInfiniteTransition()
                val scale by scaleAnim.animateFloat(
                    initialValue = 1f,
                    targetValue = 1.2f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                Canvas(modifier = Modifier.size(200.dp)) {
                    rotate(bodyRotation) {
                        drawLine(
                            color = Color.Yellow,
                            start = center,
                            end = center.copy(x = center.x + 100f),
                            strokeWidth = 15f
                        )
                    }

                    rotate(bodyRotation) {
                        translate(49f, 0f) {
                            drawRect(
                                color = Color.Red,
                                size = size.copy(width = 20.dp.toPx(), height = 20.dp.toPx())
                            )
                        }
                    }

                    rotate(bodyRotation) {
                        translate(49f, -30f) {
                            drawCircle(
                                color = Color.Black,
                                radius = 10f
                            )
                        }
                    }

                    rotate(rotation) {
                        drawLine(
                            color = Color.Gray,
                            start = center,
                            end = center.copy(x = center.x + 100f),
                            strokeWidth = 5f
                        )
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                Row (

                ) {
                    Text(
                        text = "In progress ...",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}