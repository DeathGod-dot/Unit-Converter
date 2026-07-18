package com.example.unitconverter.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.data.Category

/**
 * Hero result card — large rounded card filled with the active category's gradient.
 * Shows a faint oversized category icon, the conversion label, the huge result number,
 * and the target unit name. Animates the number when value changes.
 */
@Composable
fun ResultCard(
    category: Category,
    inputValue: String,
    fromUnit: String,
    toUnit: String,
    resultFormatted: String,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(24.dp)

    Box(
        modifier = modifier
            .shadow(
                elevation    = 24.dp,
                shape        = shape,
                ambientColor = category.accentColor.copy(alpha = 0.35f),
                spotColor    = category.accentColor.copy(alpha = 0.35f),
            )
            .clip(shape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(category.gradientStart, category.gradientEnd),
                )
            )
            .padding(horizontal = 24.dp, vertical = 28.dp),
    ) {
        // ── Faint oversized background icon (15% opacity) ─────────────────
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 16.dp, y = (-12).dp),
        ) {
            Icon(
                imageVector        = category.icon,
                contentDescription = null,
                tint               = Color.White.copy(alpha = 0.15f),
                modifier           = Modifier.size(100.dp),
            )
        }

        // ── Main content ──────────────────────────────────────────────────
        Column(
            modifier              = Modifier.fillMaxWidth(),
            horizontalAlignment   = Alignment.CenterHorizontally,
            verticalArrangement   = Arrangement.spacedBy(8.dp),
        ) {
            // Label: "{input value} {from unit} equals"
            Text(
                text          = "${inputValue.ifEmpty { "0" }} $fromUnit equals".uppercase(),
                color         = Color.White.copy(alpha = 0.75f),
                fontSize      = 11.sp,
                fontWeight    = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign     = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Huge animated result number
            AnimatedContent(
                targetState   = resultFormatted,
                transitionSpec = {
                    (fadeIn(tween(150)) + scaleIn(tween(150), initialScale = 0.95f))
                        .togetherWith(fadeOut(tween(150)) + scaleOut(tween(150), targetScale = 0.95f))
                },
                label = "resultNumber",
            ) { displayResult ->
                val digitCount = displayResult.length
                val fontSize = when {
                    digitCount <= 4  -> 60.sp
                    digitCount <= 7  -> 52.sp
                    digitCount <= 10 -> 44.sp
                    else             -> 38.sp
                }
                Text(
                    text       = displayResult.ifEmpty { "—" },
                    color      = Color.White,
                    fontSize   = fontSize,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace,
                    textAlign  = TextAlign.Center,
                    maxLines   = 2,
                    modifier   = Modifier.fillMaxWidth(),
                )
            }

            // Target unit name
            Text(
                text          = toUnit.uppercase(),
                color         = Color.White.copy(alpha = 0.90f),
                fontSize      = 14.sp,
                fontWeight    = FontWeight.Bold,
                letterSpacing = 2.sp,
                textAlign     = TextAlign.Center,
            )
        }
    }
}
