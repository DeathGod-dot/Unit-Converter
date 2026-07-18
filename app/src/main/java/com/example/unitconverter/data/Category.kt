package com.example.unitconverter.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Measurement categories supported by the converter.
 * Each category holds its display name, Material icon, unit list,
 * per-category gradient colors, and solid accent color per the design spec.
 */
enum class Category(
    val displayName: String,
    val icon: ImageVector,
    val units: List<String>,
    val gradientStart: Color,
    val gradientEnd: Color,
    val accentColor: Color,
) {
    LENGTH(
        displayName = "Length",
        icon = Icons.Filled.Straighten,
        units = listOf("m", "km", "cm", "mm", "mi", "ft", "in", "yd"),
        gradientStart = Color(0xFF22D3EE),
        gradientEnd = Color(0xFF2563EB),
        accentColor = Color(0xFF22D3EE),
    ),
    WEIGHT(
        displayName = "Weight",
        icon = Icons.Filled.Scale,
        units = listOf("kg", "g", "mg", "t", "lb", "oz"),
        gradientStart = Color(0xFFA855F7),
        gradientEnd = Color(0xFFC026D3),
        accentColor = Color(0xFFA855F7),
    ),
    TEMPERATURE(
        displayName = "Temperature",
        icon = Icons.Filled.Thermostat,
        units = listOf("°C", "°F", "K"),
        gradientStart = Color(0xFFFB923C),
        gradientEnd = Color(0xFFDC2626),
        accentColor = Color(0xFFFB923C),
    ),
    TIME(
        displayName = "Time",
        icon = Icons.Filled.Schedule,
        units = listOf("s", "min", "hr", "day", "week"),
        gradientStart = Color(0xFF34D399),
        gradientEnd = Color(0xFF0D9488),
        accentColor = Color(0xFF34D399),
    ),
    SPEED(
        displayName = "Speed",
        icon = Icons.Filled.Speed,
        units = listOf("m/s", "km/h", "mph", "knot"),
        gradientStart = Color(0xFFFBBF24),
        gradientEnd = Color(0xFFEA580C),
        accentColor = Color(0xFFFBBF24),
    ),
    AREA(
        displayName = "Area",
        icon = Icons.Filled.CropSquare,
        units = listOf("m²", "km²", "cm²", "ft²", "hectare", "acre"),
        gradientStart = Color(0xFFF472B6),
        gradientEnd = Color(0xFFE11D48),
        accentColor = Color(0xFFF472B6),
    ),
    ENERGY(
        displayName = "Energy",
        icon = Icons.Filled.Bolt,
        units = listOf("J", "kJ", "cal", "kcal", "kWh"),
        gradientStart = Color(0xFFA3E635),
        gradientEnd = Color(0xFF16A34A),
        accentColor = Color(0xFFA3E635),
    ),
}
