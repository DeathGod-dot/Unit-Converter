package com.example.unitconverter.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.data.Category
import com.example.unitconverter.theme.CardBackground
import com.example.unitconverter.theme.CardBorder
import com.example.unitconverter.theme.MutedText
import com.example.unitconverter.theme.PrimaryText

/**
 * Unit picker pill — shows current unit + dropdown chevron, outlined in the category accent color.
 * Opens a dropdown list of all units in the category on tap.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitDropdown(
    label: String,
    category: Category,
    units: List<String>,
    selectedUnit: String,
    onUnitSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val arrowRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label       = "arrowRotation",
    )

    ExposedDropdownMenuBox(
        expanded          = expanded,
        onExpandedChange  = { expanded = !expanded },
        modifier          = modifier,
    ) {
        // Pill-shaped unit picker
        Box(
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .clip(RoundedCornerShape(50))
                .background(CardBackground)
                .border(
                    width = 1.5.dp,
                    color = category.accentColor,
                    shape = RoundedCornerShape(50),
                )
                .clickable { expanded = true }
                .padding(horizontal = 14.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment    = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text       = selectedUnit,
                    color      = PrimaryText,
                    fontSize   = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Icon(
                    imageVector        = Icons.Filled.ArrowDropDown,
                    contentDescription = "Expand $label dropdown",
                    tint               = category.accentColor,
                    modifier           = Modifier
                        .size(18.dp)
                        .rotate(arrowRotation),
                )
            }
        }

        ExposedDropdownMenu(
            expanded         = expanded,
            onDismissRequest = { expanded = false },
            modifier         = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(CardBackground)
                .border(1.dp, CardBorder, RoundedCornerShape(12.dp)),
        ) {
            units.forEach { unit ->
                val isSelected = unit == selectedUnit
                DropdownMenuItem(
                    text = {
                        Text(
                            text       = unit,
                            color      = if (isSelected) category.accentColor else PrimaryText,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            fontSize   = 14.sp,
                        )
                    },
                    onClick = {
                        onUnitSelected(unit)
                        expanded = false
                    },
                    colors = MenuItemColors(
                        textColor              = PrimaryText,
                        leadingIconColor       = category.accentColor,
                        trailingIconColor      = category.accentColor,
                        disabledTextColor      = MutedText,
                        disabledLeadingIconColor  = MutedText,
                        disabledTrailingIconColor = MutedText,
                    ),
                )
            }
        }
    }
}
