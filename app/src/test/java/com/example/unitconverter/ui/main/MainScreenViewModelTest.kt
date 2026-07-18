package com.example.unitconverter.ui.main

import com.example.unitconverter.data.Category
import com.example.unitconverter.data.ConversionLogic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ConverterViewModelTest {

    @Test
    fun initialState_isLength() {
        val viewModel = ConverterViewModel()
        assertEquals(Category.LENGTH, viewModel.uiState.value.category)
        assertTrue(viewModel.uiState.value.result.isEmpty())
    }

    @Test
    fun convert_emptyInput_setsError() {
        val viewModel = ConverterViewModel()
        viewModel.onInputChange("")
        viewModel.convert()
        assertEquals("Please enter a value to convert", viewModel.uiState.value.errorMessage)
    }

    @Test
    fun convert_nonNumericInput_setsError() {
        val viewModel = ConverterViewModel()
        viewModel.onInputChange("abc")
        viewModel.convert()
        assertEquals("Please enter a valid number", viewModel.uiState.value.errorMessage)
    }

    @Test
    fun convert_validLength_producesResult() {
        val viewModel = ConverterViewModel()
        viewModel.onInputChange("1")
        viewModel.onFromUnitChange("Kilometre")
        viewModel.onToUnitChange("Metre")
        viewModel.convert()
        assertNull(viewModel.uiState.value.errorMessage)
        assertTrue(viewModel.uiState.value.result.contains("1000"))
    }

    @Test
    fun convert_temperature_celsiusToFahrenheit() {
        val viewModel = ConverterViewModel()
        viewModel.onCategoryChange(Category.TEMPERATURE)
        viewModel.onInputChange("0")
        viewModel.onFromUnitChange("Celsius")
        viewModel.onToUnitChange("Fahrenheit")
        viewModel.convert()
        assertTrue(viewModel.uiState.value.result.contains("32"))
    }

    @Test
    fun categoryChange_resetsResult() {
        val viewModel = ConverterViewModel()
        viewModel.onInputChange("100")
        viewModel.onFromUnitChange("Metre")
        viewModel.onToUnitChange("Foot")
        viewModel.convert()
        assertTrue(viewModel.uiState.value.result.isNotEmpty())
        viewModel.onCategoryChange(Category.WEIGHT)
        assertTrue(viewModel.uiState.value.result.isEmpty())
    }

    @Test
    fun conversionLogic_kilometre_to_metre() {
        val result = ConversionLogic.convert(Category.LENGTH, 1.0, "Kilometre", "Metre")
        assertEquals(1000.0, result, 0.0001)
    }

    @Test
    fun conversionLogic_celsius_to_kelvin() {
        val result = ConversionLogic.convert(Category.TEMPERATURE, 0.0, "Celsius", "Kelvin")
        assertEquals(273.15, result, 0.0001)
    }
}
