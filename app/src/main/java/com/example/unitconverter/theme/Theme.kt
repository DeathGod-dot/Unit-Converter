package com.example.unitconverter.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Inline constant — avoids forward-reference issue
private val ColorBlack = Color(0xFF000000)

private val DarkColorScheme = darkColorScheme(
    primary = Indigo80,
    secondary = IndigoGrey80,
    tertiary = Teal80,
    background = DeepIndigo,
    surface = SurfaceDark,
    surfaceContainer = SurfaceContainer,
    onPrimary = ColorBlack,
    onSecondary = ColorBlack,
    onBackground = OnSurfaceLight,
    onSurface = OnSurfaceLight,
    onSurfaceVariant = OnSurfaceDim,
)

@Composable
fun UnitConverterTheme(
    content: @Composable () -> Unit,
) {
    // Always use dark scheme for a premium, consistent look
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content,
    )
}
