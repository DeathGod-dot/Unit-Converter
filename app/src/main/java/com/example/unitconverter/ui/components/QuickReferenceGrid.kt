package com.example.unitconverter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.data.Category
import com.example.unitconverter.theme.CardBackground
import com.example.unitconverter.theme.MutedText
import com.example.unitconverter.theme.PrimaryText

/**
 * Quick reference grid — "ALL CONVERSIONS" header + a 2-column grid of small cards,
 * one per remaining unit in the category (every unit except the current "from").
 * Each card has a 3dp left border stripe in the category's accent color,
 * a #141721 background, the converted value (bold monospace), and the unit name below.
 */
@Composable
fun QuickReferenceGrid(
    category: Category,
    fromUnit: String,
    inputValue: String,
    convertTo: (String) -> String,
    modifier: Modifier = Modifier,
) {
    val remainingUnits = category.units.filter { it != fromUnit }

    Column(
        modifier          = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Section header
        Text(
            text          = "All Conversions",
            color         = MutedText,
            fontSize      = 11.sp,
            fontWeight    = FontWeight.Bold,
            letterSpacing = 1.sp,
        )

        // 2-column grid of conversion cards
        // Using a Column + chunked rows to avoid nested lazy scrolling issues
        val rows = remainingUnits.chunked(2)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            rows.forEach { rowUnits ->
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    rowUnits.forEach { unit ->
                        val converted = if (inputValue.trim().toDoubleOrNull() != null) {
                            convertTo(unit)
                        } else {
                            "—"
                        }
                        QuickRefCard(
                            unit          = unit,
                            value         = converted,
                            accentColor   = category.accentColor,
                            modifier      = Modifier.weight(1f),
                        )
                    }
                    // If odd number of units in last row, add spacer to balance
                    if (rowUnits.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickRefCard(
    unit: String,
    value: String,
    accentColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(CardBackground)
            .border(0.5.dp, accentColor.copy(alpha = 0.2f), RoundedCornerShape(10.dp)),
    ) {
        // 3dp left border stripe
        Box(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight()
                .background(accentColor)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(3.dp),
        ) {
            Text(
                text       = value,
                color      = PrimaryText,
                fontSize   = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                maxLines   = 1,
            )
            Text(
                text          = unit.uppercase(),
                color         = MutedText,
                fontSize      = 10.sp,
                fontWeight    = FontWeight.Medium,
                letterSpacing = 0.5.sp,
            )
        }
    }
}
