package com.example.unitconverter.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    background        = AppBackground,
    surface           = CardBackground,
    surfaceContainer  = CardBackground,
    onBackground      = PrimaryText,
    onSurface         = PrimaryText,
    onSurfaceVariant  = MutedText,
    outline           = CardBorder,
    primary           = Color.White,
    onPrimary         = Color.Black,
)

@Composable
fun UnitConverterTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography  = Typography,
        content     = content,
    )
}
