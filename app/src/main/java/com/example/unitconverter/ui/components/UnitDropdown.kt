package com.example.unitconverter.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.theme.AccentTeal
import com.example.unitconverter.theme.CardSurface
import com.example.unitconverter.theme.OnSurfaceDim
import com.example.unitconverter.theme.OnSurfaceLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitDropdown(
    label: String,
    units: List<String>,
    selectedUnit: String,
    onUnitSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val arrowRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "arrowRotation",
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = selectedUnit,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = label,
                    color = OnSurfaceDim,
                    fontSize = 12.sp,
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Expand $label dropdown",
                    tint = AccentTeal,
                    modifier = Modifier.rotate(arrowRotation),
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = OnSurfaceLight,
                unfocusedTextColor = OnSurfaceLight,
                focusedContainerColor = CardSurface,
                unfocusedContainerColor = CardSurface,
                focusedBorderColor = AccentTeal,
                unfocusedBorderColor = Color(0xFF3D3B5E),
                focusedLabelColor = AccentTeal,
                unfocusedLabelColor = OnSurfaceDim,
                cursorColor = AccentTeal,
            ),
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(CardSurface)
                .border(1.dp, Color(0xFF3D3B5E), RoundedCornerShape(14.dp)),
        ) {
            units.forEach { unit ->
                val isSelected = unit == selectedUnit
                DropdownMenuItem(
                    text = {
                        Text(
                            text = unit,
                            color = if (isSelected) AccentTeal else OnSurfaceLight,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            fontSize = 14.sp,
                        )
                    },
                    onClick = {
                        onUnitSelected(unit)
                        expanded = false
                    },
                    colors = MenuItemColors(
                        textColor = OnSurfaceLight,
                        leadingIconColor = AccentTeal,
                        trailingIconColor = AccentTeal,
                        disabledTextColor = OnSurfaceDim,
                        disabledLeadingIconColor = OnSurfaceDim,
                        disabledTrailingIconColor = OnSurfaceDim,
                    ),
                )
            }
        }
    }
}
