package com.example.unitconverter.ui.main

import androidx.lifecycle.ViewModel
import com.example.unitconverter.data.Category
import com.example.unitconverter.data.ConversionLogic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ConverterUiState(
    val category: Category = Category.LENGTH,
    val inputValue: String = "1",
    val fromUnit: String = Category.LENGTH.units.first(),
    val toUnit: String = Category.LENGTH.units[1],
    // Converted result as a formatted string (empty if input is invalid)
    val resultFormatted: String = "",
    // Raw converted double for quick reference grid
    val resultRaw: Double? = null,
)

class ConverterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ConverterUiState())
    val uiState: StateFlow<ConverterUiState> = _uiState.asStateFlow()

    init {
        // Compute initial result for default state
        recalculate()
    }

    fun onCategoryChange(newCategory: Category) {
        _uiState.update {
            it.copy(
                category  = newCategory,
                fromUnit  = newCategory.units.first(),
                toUnit    = newCategory.units.getOrElse(1) { newCategory.units.first() },
                inputValue = "1",
            )
        }
        recalculate()
    }

    fun onInputChange(input: String) {
        _uiState.update { it.copy(inputValue = input) }
        recalculate()
    }

    fun onFromUnitChange(unit: String) {
        _uiState.update { it.copy(fromUnit = unit) }
        recalculate()
    }

    fun onToUnitChange(unit: String) {
        _uiState.update { it.copy(toUnit = unit) }
        recalculate()
    }

    fun swap() {
        _uiState.update {
            it.copy(
                fromUnit = it.toUnit,
                toUnit   = it.fromUnit,
            )
        }
        recalculate()
    }

    /**
     * Converts the current input value and updates the result.
     * Called after any state change that affects conversion.
     */
    private fun recalculate() {
        val state = _uiState.value
        val raw = state.inputValue.trim()
        val value = raw.toDoubleOrNull()

        if (value == null || raw.isEmpty()) {
            _uiState.update { it.copy(resultFormatted = "", resultRaw = null) }
            return
        }

        val converted = try {
            ConversionLogic.convert(state.category, value, state.fromUnit, state.toUnit)
        } catch (_: Exception) {
            _uiState.update { it.copy(resultFormatted = "", resultRaw = null) }
            return
        }

        _uiState.update {
            it.copy(
                resultFormatted = ConversionLogic.formatNumber(converted),
                resultRaw       = converted,
            )
        }
    }

    /**
     * Convert [value] from [fromUnit] to [targetUnit] in current category.
     * Used for quick reference grid items.
     */
    fun convertTo(targetUnit: String): String {
        val state = _uiState.value
        val value = state.inputValue.trim().toDoubleOrNull() ?: return ""
        return try {
            val result = ConversionLogic.convert(state.category, value, state.fromUnit, targetUnit)
            ConversionLogic.formatNumber(result)
        } catch (_: Exception) {
            ""
        }
    }
}
