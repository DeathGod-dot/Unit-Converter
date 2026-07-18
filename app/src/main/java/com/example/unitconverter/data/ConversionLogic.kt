package com.example.unitconverter.data

/**
 * Pure conversion engine. Strategy: convert source value → SI base unit → convert to target unit.
 * Temperature is handled via formulas since there is no simple multiplier.
 */
object ConversionLogic {

    // ─── Length (base: metre) ────────────────────────────────────────────────
    private val lengthToMetre = mapOf(
        "Millimetre"  to 0.001,
        "Centimetre"  to 0.01,
        "Metre"       to 1.0,
        "Kilometre"   to 1_000.0,
        "Inch"        to 0.0254,
        "Foot"        to 0.3048,
        "Yard"        to 0.9144,
        "Mile"        to 1_609.344,
    )

    // ─── Weight (base: gram) ─────────────────────────────────────────────────
    private val weightToGram = mapOf(
        "Milligram"   to 0.001,
        "Gram"        to 1.0,
        "Kilogram"    to 1_000.0,
        "Tonne"       to 1_000_000.0,
        "Ounce"       to 28.3495,
        "Pound"       to 453.592,
        "Stone"       to 6_350.29,
    )

    // ─── Volume (base: millilitre) ───────────────────────────────────────────
    private val volumeToMl = mapOf(
        "Millilitre"  to 1.0,
        "Litre"       to 1_000.0,
        "Fluid Ounce" to 29.5735,
        "Cup"         to 236.588,
        "Pint"        to 473.176,
        "Gallon"      to 3_785.41,
    )

    /**
     * Convert [value] from [from] unit to [to] unit within [category].
     * Returns the converted Double or throws [IllegalArgumentException] for unknown units.
     */
    fun convert(category: Category, value: Double, from: String, to: String): Double {
        if (from == to) return value
        return when (category) {
            Category.LENGTH      -> convertViaBase(value, from, to, lengthToMetre)
            Category.WEIGHT      -> convertViaBase(value, from, to, weightToGram)
            Category.VOLUME      -> convertViaBase(value, from, to, volumeToMl)
            Category.TEMPERATURE -> convertTemperature(value, from, to)
        }
    }

    private fun convertViaBase(value: Double, from: String, to: String, table: Map<String, Double>): Double {
        val fromFactor = table[from] ?: error("Unknown unit: $from")
        val toFactor   = table[to]   ?: error("Unknown unit: $to")
        return value * fromFactor / toFactor
    }

    private fun convertTemperature(value: Double, from: String, to: String): Double {
        // Step 1: convert to Celsius
        val celsius = when (from) {
            "Celsius"    -> value
            "Fahrenheit" -> (value - 32.0) * 5.0 / 9.0
            "Kelvin"     -> value - 273.15
            else         -> error("Unknown temperature unit: $from")
        }
        // Step 2: convert Celsius to target
        return when (to) {
            "Celsius"    -> celsius
            "Fahrenheit" -> celsius * 9.0 / 5.0 + 32.0
            "Kelvin"     -> celsius + 273.15
            else         -> error("Unknown temperature unit: $to")
        }
    }
}
