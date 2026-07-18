package com.example.unitconverter.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.data.Category
import com.example.unitconverter.theme.ChipInactiveBorder
import com.example.unitconverter.theme.ChipInactiveFill
import com.example.unitconverter.theme.ChipInactiveText

@Composable
fun CategorySelector(
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 4.dp),
    ) {
        items(Category.entries) { category ->
            CategoryChip(
                category   = category,
                isSelected = category == selectedCategory,
                onClick    = { onCategorySelected(category) },
            )
        }
    }
}

@Composable
private fun CategoryChip(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(50)

    if (isSelected) {
        // Active chip: gradient fill + colored glow shadow
        Box(
            modifier = Modifier
                .shadow(
                    elevation    = 10.dp,
                    shape        = shape,
                    ambientColor = category.accentColor.copy(alpha = 0.45f),
                    spotColor    = category.accentColor.copy(alpha = 0.45f),
                )
                .clip(shape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(category.gradientStart, category.gradientEnd),
                    )
                )
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 9.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment    = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    imageVector       = category.icon,
                    contentDescription = null,
                    tint              = Color.White,
                    modifier          = Modifier.size(15.dp),
                )
                Text(
                    text       = category.displayName.uppercase(),
                    color      = Color.White,
                    fontSize   = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp,
                )
            }
        }
    } else {
        // Inactive chip: outlined border, muted fill and text
        Box(
            modifier = Modifier
                .clip(shape)
                .background(ChipInactiveFill)
                .border(
                    width = 1.dp,
                    color = ChipInactiveBorder,
                    shape = shape,
                )
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 9.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment    = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    imageVector       = category.icon,
                    contentDescription = null,
                    tint              = ChipInactiveText,
                    modifier          = Modifier.size(15.dp),
                )
                Text(
                    text       = category.displayName.uppercase(),
                    color      = ChipInactiveText,
                    fontSize   = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp,
                )
            }
        }
    }
}
