package com.example.unitconverter.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.data.Category
import com.example.unitconverter.theme.CardBackground
import com.example.unitconverter.theme.CardBorder
import com.example.unitconverter.theme.ControlsReadOnly
import com.example.unitconverter.theme.MutedText
import com.example.unitconverter.theme.PrimaryText

/**
 * Controls row: From side | Swap button | To side.
 * Card with #141721 background and #1F232E border.
 */
@Composable
fun ControlsRow(
    category: Category,
    fromUnit: String,
    toUnit: String,
    inputValue: String,
    resultFormatted: String,
    onInputChange: (String) -> Unit,
    onFromUnitChange: (String) -> Unit,
    onToUnitChange: (String) -> Unit,
    onSwap: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    // Swap animation — rotates 180° on each tap
    var swapRotation by remember { mutableFloatStateOf(0f) }
    val animatedRotation by animateFloatAsState(
        targetValue  = swapRotation,
        animationSpec = tween(durationMillis = 300),
        label        = "swapRotation",
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CardBackground)
            .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth(),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // ── Left: From side ──────────────────────────────────────────
            Column(
                modifier            = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text          = "FROM",
                    color         = MutedText,
                    fontSize      = 10.sp,
                    fontWeight    = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                )
                UnitDropdown(
                    label          = "From",
                    category       = category,
                    units          = category.units,
                    selectedUnit   = fromUnit,
                    onUnitSelected = onFromUnitChange,
                )
                // Large bold editable number input
                BasicTextField(
                    value       = inputValue,
                    onValueChange = onInputChange,
                    textStyle   = TextStyle(
                        color      = PrimaryText,
                        fontSize   = 36.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        textAlign  = TextAlign.Start,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction    = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() },
                    ),
                    cursorBrush = SolidColor(category.accentColor),
                    singleLine  = true,
                    decorationBox = { innerField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width  = 0.dp,
                                    color  = Color.Transparent,
                                    shape  = RoundedCornerShape(0.dp),
                                )
                        ) {
                            if (inputValue.isEmpty()) {
                                Text(
                                    text      = "0",
                                    color     = MutedText,
                                    fontSize  = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                )
                            }
                            innerField()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
                // Underline — accent color on focus
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.5.dp)
                        .background(category.accentColor.copy(alpha = 0.6f))
                )
            }

            // ── Center: Swap button ──────────────────────────────────────
            Box(
                modifier          = Modifier.padding(horizontal = 12.dp),
                contentAlignment  = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .shadow(
                            elevation    = 8.dp,
                            shape        = CircleShape,
                            ambientColor = category.accentColor.copy(alpha = 0.45f),
                            spotColor    = category.accentColor.copy(alpha = 0.45f),
                        )
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(category.gradientStart, category.gradientEnd),
                            )
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    IconButton(
                        onClick = {
                            swapRotation += 180f
                            onSwap()
                        },
                        modifier = Modifier.size(44.dp),
                    ) {
                        Icon(
                            imageVector        = Icons.Filled.SwapHoriz,
                            contentDescription = "Swap units",
                            tint               = Color.White,
                            modifier           = Modifier
                                .size(22.dp)
                                .rotate(animatedRotation),
                        )
                    }
                }
            }

            // ── Right: To side ───────────────────────────────────────────
            Column(
                modifier            = Modifier.weight(1f),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text          = "TO",
                    color         = MutedText,
                    fontSize      = 10.sp,
                    fontWeight    = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                )
                UnitDropdown(
                    label          = "To",
                    category       = category,
                    units          = category.units,
                    selectedUnit   = toUnit,
                    onUnitSelected = onToUnitChange,
                )
                // Read-only result number
                Text(
                    text       = resultFormatted.ifEmpty { "—" },
                    color      = ControlsReadOnly,
                    fontSize   = 36.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    textAlign  = TextAlign.End,
                    maxLines   = 1,
                    modifier   = Modifier.fillMaxWidth(),
                )
                // Underline (muted)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.5.dp)
                        .background(CardBorder)
                )
            }
        }
    }
}
