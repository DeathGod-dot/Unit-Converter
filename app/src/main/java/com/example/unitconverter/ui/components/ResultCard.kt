package com.example.unitconverter.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.theme.AccentTeal
import com.example.unitconverter.theme.AccentTealDim
import com.example.unitconverter.theme.CardSurface
import com.example.unitconverter.theme.OnSurfaceDim
import com.example.unitconverter.theme.OnSurfaceLight
import com.example.unitconverter.theme.ResultGold

@Composable
fun ResultCard(
    result: String,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = result.isNotEmpty(),
        enter = fadeIn(animationSpec = tween(300)) +
                slideInVertically(
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                    initialOffsetY = { it / 2 },
                ),
        exit = fadeOut(animationSpec = tween(200)) +
                slideOutVertically(
                    animationSpec = tween(200),
                    targetOffsetY = { it / 2 },
                ),
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            CardSurface,
                            CardSurface.copy(alpha = 0.9f),
                        )
                    )
                )
                .border(
                    width = 1.5.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(AccentTeal, AccentTealDim),
                    ),
                    shape = RoundedCornerShape(20.dp),
                )
                .padding(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "Result",
                    color = OnSurfaceDim,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.5.sp,
                )
                // Parse and style the result expression: "X fromUnit = Y toUnit"
                val parts = result.split(" = ")
                if (parts.size == 2) {
                    Text(
                        text = parts[0],
                        color = OnSurfaceLight,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = "=",
                        color = AccentTeal,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = parts[1],
                        color = ResultGold,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                } else {
                    Text(
                        text = result,
                        color = OnSurfaceLight,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}
