package com.example.unitconverter.data

/**
 * Measurement categories supported by the converter.
 * Each category holds its display name, emoji icon, and available unit list.
 */
enum class Category(
    val displayName: String,
    val icon: String,
    val units: List<String>,
) {
    LENGTH(
        displayName = "Length",
        icon = "📏",
        units = listOf(
            "Millimetre", "Centimetre", "Metre", "Kilometre",
            "Inch", "Foot", "Yard", "Mile",
        ),
    ),
    WEIGHT(
        displayName = "Weight",
        icon = "⚖️",
        units = listOf(
            "Milligram", "Gram", "Kilogram", "Tonne",
            "Ounce", "Pound", "Stone",
        ),
    ),
    TEMPERATURE(
        displayName = "Temperature",
        icon = "🌡️",
        units = listOf("Celsius", "Fahrenheit", "Kelvin"),
    ),
    VOLUME(
        displayName = "Volume",
        icon = "🧪",
        units = listOf(
            "Millilitre", "Litre", "Fluid Ounce", "Cup", "Pint", "Gallon",
        ),
    ),
}
