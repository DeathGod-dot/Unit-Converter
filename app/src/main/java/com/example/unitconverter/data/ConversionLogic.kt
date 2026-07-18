package com.example.unitconverter.data

/**
 * Pure conversion engine.
 * Strategy: convert source value → SI base unit → convert to target unit.
 * Temperature uses formulas (no simple multiplier due to offset).
 *
 * Conversion factors per spec:
 *   Length  (base m):   m=1, km=1000, cm=0.01, mi=1609.344, ft=0.3048, in=0.0254
 *   Weight  (base kg):  kg=1, g=0.001, mg=0.000001, t=1000, lb=0.45359237, oz=0.0283495231
 *   Time    (base s):   s=1, min=60, hr=3600, day=86400, week=604800
 *   Speed   (base m/s): m/s=1, km/h=0.27777778, mph=0.44704, knot=0.5144444
 *   Area    (base m²):  m²=1, km²=1000000, hectare=10000, acre=4046.8564224, ft²=0.09290304, cm²=0.0001
 *   Energy  (base J):   J=1, kJ=1000, cal=4.184, kcal=4184, kWh=3600000
 */
object ConversionLogic {

    // ─── Length (base: metre) ────────────────────────────────────────────────
    private val lengthToMetre = mapOf(
        "m"   to 1.0,
        "km"  to 1_000.0,
        "cm"  to 0.01,
        "mm"  to 0.001,
        "mi"  to 1_609.344,
        "ft"  to 0.3048,
        "in"  to 0.0254,
        "yd"  to 0.9144,
    )

    // ─── Weight (base: kilogram) ─────────────────────────────────────────────
    private val weightToKg = mapOf(
        "kg"  to 1.0,
        "g"   to 0.001,
        "mg"  to 0.000_001,
        "t"   to 1_000.0,
        "lb"  to 0.45359237,
        "oz"  to 0.028_349_523_1,
    )

    // ─── Time (base: second) ─────────────────────────────────────────────────
    private val timeToSecond = mapOf(
        "s"    to 1.0,
        "min"  to 60.0,
        "hr"   to 3_600.0,
        "day"  to 86_400.0,
        "week" to 604_800.0,
    )

    // ─── Speed (base: m/s) ───────────────────────────────────────────────────
    private val speedToMs = mapOf(
        "m/s"  to 1.0,
        "km/h" to 0.277_777_78,
        "mph"  to 0.44704,
        "knot" to 0.514_444_4,
    )

    // ─── Area (base: m²) ─────────────────────────────────────────────────────
    private val areaToM2 = mapOf(
        "m²"      to 1.0,
        "km²"     to 1_000_000.0,
        "cm²"     to 0.000_1,
        "ft²"     to 0.092_903_04,
        "hectare" to 10_000.0,
        "acre"    to 4_046.856_422_4,
    )

    // ─── Energy (base: Joule) ────────────────────────────────────────────────
    private val energyToJoule = mapOf(
        "J"   to 1.0,
        "kJ"  to 1_000.0,
        "cal" to 4.184,
        "kcal" to 4_184.0,
        "kWh" to 3_600_000.0,
    )

    /**
     * Convert [value] from [from] unit to [to] unit within [category].
     * Returns the converted Double.
     */
    fun convert(category: Category, value: Double, from: String, to: String): Double {
        if (from == to) return value
        return when (category) {
            Category.LENGTH      -> convertViaBase(value, from, to, lengthToMetre)
            Category.WEIGHT      -> convertViaBase(value, from, to, weightToKg)
            Category.TEMPERATURE -> convertTemperature(value, from, to)
            Category.TIME        -> convertViaBase(value, from, to, timeToSecond)
            Category.SPEED       -> convertViaBase(value, from, to, speedToMs)
            Category.AREA        -> convertViaBase(value, from, to, areaToM2)
            Category.ENERGY      -> convertViaBase(value, from, to, energyToJoule)
        }
    }

    private fun convertViaBase(
        value: Double,
        from: String,
        to: String,
        table: Map<String, Double>,
    ): Double {
        val fromFactor = table[from] ?: error("Unknown unit: $from")
        val toFactor   = table[to]   ?: error("Unknown unit: $to")
        return value * fromFactor / toFactor
    }

    private fun convertTemperature(value: Double, from: String, to: String): Double {
        // Step 1: convert to Celsius
        val celsius = when (from) {
            "°C" -> value
            "°F" -> (value - 32.0) * 5.0 / 9.0
            "K"  -> value - 273.15
            else -> error("Unknown temperature unit: $from")
        }
        // Step 2: convert Celsius to target
        return when (to) {
            "°C" -> celsius
            "°F" -> celsius * 9.0 / 5.0 + 32.0
            "K"  -> celsius + 273.15
            else -> error("Unknown temperature unit: $to")
        }
    }

    /**
     * Format a result number per spec:
     * - Scientific notation for values ≥ 1e9 or < 1e-6 (and non-zero)
     * - Trim trailing zeros
     * - Up to 6 significant figures
     */
    fun formatNumber(value: Double): String {
        if (value == 0.0) return "0"
        val abs = Math.abs(value)
        return if (abs >= 1e9 || (abs < 1e-6 && abs > 0)) {
            "%.4e".format(value)
                .replace(Regex("0+e"), "e")
                .replace(Regex("\\.e"), "e")
        } else {
            // Up to 6 significant figures, trim trailing zeros
            val formatted = "%.6g".format(value)
            if (formatted.contains('.')) {
                formatted.trimEnd('0').trimEnd('.')
            } else {
                formatted
            }
        }
    }
}
