package com.example.unitconverter.ui.main

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitconverter.ui.components.CategorySelector
import com.example.unitconverter.ui.components.ResultCard
import com.example.unitconverter.ui.components.UnitDropdown
import com.example.unitconverter.theme.*

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: ConverterViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    // Show Toast on error
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DeepIndigo)
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ── Top App Bar with gradient ──────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(GradientStart, GradientEnd),
                        )
                    )
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Calculate,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp),
                        )
                        Text(
                            text = "Unit Converter",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Fast & accurate unit conversion",
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 13.sp,
                    )
                }
            }

            // ── Scrollable content ─────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                // Category selector
                SectionLabel("Category")
                CategorySelector(
                    selectedCategory = state.category,
                    onCategorySelected = viewModel::onCategoryChange,
                )

                HorizontalDivider(color = Color(0xFF3D3B5E), thickness = 1.dp)

                // Input field
                SectionLabel("Value to Convert")
                OutlinedTextField(
                    value = state.inputValue,
                    onValueChange = viewModel::onInputChange,
                    placeholder = {
                        Text(
                            "Enter a number…",
                            color = OnSurfaceDim,
                            fontSize = 16.sp,
                        )
                    },
                    textStyle = TextStyle(
                        color = OnSurfaceLight,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            viewModel.convert()
                        }
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = CardSurface,
                        unfocusedContainerColor = CardSurface,
                        focusedBorderColor = AccentTeal,
                        unfocusedBorderColor = Color(0xFF3D3B5E),
                        focusedTextColor = OnSurfaceLight,
                        unfocusedTextColor = OnSurfaceLight,
                        cursorColor = AccentTeal,
                    ),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )

                HorizontalDivider(color = Color(0xFF3D3B5E), thickness = 1.dp)

                // Unit selectors
                SectionLabel("Units")
                UnitDropdown(
                    label = "From",
                    units = state.category.units,
                    selectedUnit = state.fromUnit,
                    onUnitSelected = viewModel::onFromUnitChange,
                    modifier = Modifier.fillMaxWidth(),
                )

                // Swap button
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    var swapPressed by remember { mutableStateOf(false) }
                    val swapScale by animateFloatAsState(
                        targetValue = if (swapPressed) 0.85f else 1f,
                        animationSpec = spring(),
                        label = "swapScale",
                    )
                    FilledTonalIconButton(
                        onClick = {
                            val newFrom = state.toUnit
                            val newTo = state.fromUnit
                            viewModel.onFromUnitChange(newFrom)
                            viewModel.onToUnitChange(newTo)
                        },
                        modifier = Modifier
                            .size(44.dp)
                            .scale(swapScale),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = GradientStart.copy(alpha = 0.6f),
                            contentColor = AccentTeal,
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.SwapVert,
                            contentDescription = "Swap units",
                        )
                    }
                }

                UnitDropdown(
                    label = "To",
                    units = state.category.units,
                    selectedUnit = state.toUnit,
                    onUnitSelected = viewModel::onToUnitChange,
                    modifier = Modifier.fillMaxWidth(),
                )

                // Convert button
                ConvertButton(onClick = {
                    focusManager.clearFocus()
                    viewModel.convert()
                })

                // Result card
                ResultCard(
                    result = state.result,
                    modifier = Modifier.fillMaxWidth(),
                )

                // Bottom spacer for comfortable scrolling
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        color = OnSurfaceDim,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 1.5.sp,
    )
}

@Composable
private fun ConvertButton(onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        animationSpec = spring(),
        label = "btnScale",
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            ),
            contentPadding = PaddingValues(0.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(GradientStart, GradientEnd),
                        ),
                        shape = RoundedCornerShape(16.dp),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Calculate,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp),
                    )
                    Text(
                        text = "Convert",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                    )
                }
            }
        }
    }
}
