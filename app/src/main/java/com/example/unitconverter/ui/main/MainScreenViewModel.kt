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
    val inputValue: String = "",
    val fromUnit: String = Category.LENGTH.units.first(),
    val toUnit: String = Category.LENGTH.units[1],
    val result: String = "",
    val errorMessage: String? = null,
)

class ConverterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ConverterUiState())
    val uiState: StateFlow<ConverterUiState> = _uiState.asStateFlow()

    fun onCategoryChange(newCategory: Category) {
        _uiState.update {
            it.copy(
                category = newCategory,
                fromUnit = newCategory.units.first(),
                toUnit = newCategory.units.getOrElse(1) { newCategory.units.first() },
                result = "",
                errorMessage = null,
                inputValue = "",
            )
        }
    }

    fun onInputChange(input: String) {
        _uiState.update { it.copy(inputValue = input, errorMessage = null) }
    }

    fun onFromUnitChange(unit: String) {
        _uiState.update { it.copy(fromUnit = unit, result = "") }
    }

    fun onToUnitChange(unit: String) {
        _uiState.update { it.copy(toUnit = unit, result = "") }
    }

    fun convert() {
        val state = _uiState.value
        val raw = state.inputValue.trim()

        // Validation
        if (raw.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "Please enter a value to convert") }
            return
        }
        val value = raw.toDoubleOrNull()
        if (value == null) {
            _uiState.update { it.copy(errorMessage = "Please enter a valid number") }
            return
        }

        val converted = try {
            ConversionLogic.convert(state.category, value, state.fromUnit, state.toUnit)
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = "Conversion error: ${e.message}") }
            return
        }

        val formatted = formatResult(value, state.fromUnit, converted, state.toUnit)
        _uiState.update { it.copy(result = formatted, errorMessage = null) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    private fun formatResult(
        inputVal: Double, fromUnit: String,
        outputVal: Double, toUnit: String,
    ): String {
        val formattedInput = formatNumber(inputVal)
        val formattedOutput = formatNumber(outputVal)
        return "$formattedInput $fromUnit = $formattedOutput $toUnit"
    }

    private fun formatNumber(value: Double): String {
        return if (value == value.toLong().toDouble() && !value.isInfinite()) {
            value.toLong().toString()
        } else {
            // Up to 8 significant figures, trim trailing zeros
            "%.8g".format(value).trimEnd('0').trimEnd('.')
        }
    }
}
