package com.example.unitconverter.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitconverter.theme.AppBackground
import com.example.unitconverter.theme.MutedText
import com.example.unitconverter.ui.components.CategorySelector
import com.example.unitconverter.ui.components.ControlsRow
import com.example.unitconverter.ui.components.QuickReferenceGrid
import com.example.unitconverter.ui.components.ResultCard

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: ConverterViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val category = state.category

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackground)
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // ── 1. Header row ─────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
            ) {
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    // Wordmark + subtitle
                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        // "Convert." with accent-colored dot
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        color      = Color.White,
                                        fontSize   = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                ) { append("Convert") }
                                withStyle(
                                    SpanStyle(
                                        color      = category.accentColor,
                                        fontSize   = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                ) { append(".") }
                            },
                        )
                        Text(
                            text      = "7 categories · instant conversion",
                            color     = MutedText,
                            fontSize  = 12.sp,
                            fontWeight = FontWeight.Normal,
                        )
                    }

                    // 44dp rounded-square icon badge — category gradient + white icon + glow
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .shadow(
                                elevation    = 12.dp,
                                shape        = RoundedCornerShape(12.dp),
                                ambientColor = category.accentColor.copy(alpha = 0.45f),
                                spotColor    = category.accentColor.copy(alpha = 0.45f),
                            )
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(category.gradientStart, category.gradientEnd),
                                )
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector        = category.icon,
                            contentDescription = null,
                            tint               = Color.White,
                            modifier           = Modifier.size(24.dp),
                        )
                    }
                }
            }

            // ── Scrollable content ────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {

                // ── 2. Category selector ──────────────────────────────────────
                CategorySelector(
                    selectedCategory  = category,
                    onCategorySelected = viewModel::onCategoryChange,
                    modifier          = Modifier.fillMaxWidth(),
                )

                // ── 3. Hero result card ───────────────────────────────────────
                ResultCard(
                    category        = category,
                    inputValue      = state.inputValue,
                    fromUnit        = state.fromUnit,
                    toUnit          = state.toUnit,
                    resultFormatted = state.resultFormatted,
                    modifier        = Modifier.fillMaxWidth(),
                )

                // ── 4. Controls row (From / Swap / To) ────────────────────────
                ControlsRow(
                    category         = category,
                    fromUnit         = state.fromUnit,
                    toUnit           = state.toUnit,
                    inputValue       = state.inputValue,
                    resultFormatted  = state.resultFormatted,
                    onInputChange    = viewModel::onInputChange,
                    onFromUnitChange = viewModel::onFromUnitChange,
                    onToUnitChange   = viewModel::onToUnitChange,
                    onSwap           = viewModel::swap,
                    modifier         = Modifier.fillMaxWidth(),
                )

                // ── 5. Quick reference grid ───────────────────────────────────
                QuickReferenceGrid(
                    category   = category,
                    fromUnit   = state.fromUnit,
                    inputValue = state.inputValue,
                    convertTo  = viewModel::convertTo,
                    modifier   = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
