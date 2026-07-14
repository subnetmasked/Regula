package org.regula.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.regula.app.ui.theme.CardShape
import org.regula.app.ui.theme.RegulaGold
import org.regula.app.ui.theme.RegulaGoldDim
import org.regula.app.ui.theme.RegulaGoldMuted
import org.regula.app.ui.theme.RegulaSurfaceElevated
import org.regula.app.ui.theme.RegulaSurfaceGlass

@Composable
fun GlassCard(
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    accentColor: Color = RegulaGold,
    content: @Composable ColumnScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val clickableModifier = if (onClick != null) {
        modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick,
        )
    } else {
        modifier
    }

    Surface(
        modifier = clickableModifier.fillMaxWidth(),
        shape = CardShape,
        color = RegulaSurfaceGlass,
        border = BorderStroke(
            width = 1.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    accentColor.copy(alpha = 0.55f),
                    RegulaGoldMuted,
                    RegulaGoldDim.copy(alpha = 0.25f),
                ),
            ),
        ),
        shadowElevation = 8.dp,
        tonalElevation = 2.dp,
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            content = content,
        )
    }
}

@Composable
fun ElevatedCard(
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    GlassCard(onClick = onClick, modifier = modifier, content = content)
}

@Composable
fun SolidCard(
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val clickableModifier = if (onClick != null) {
        modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick,
        )
    } else {
        modifier
    }

    Surface(
        modifier = clickableModifier.fillMaxWidth(),
        shape = CardShape,
        color = RegulaSurfaceElevated,
        border = BorderStroke(1.dp, RegulaGoldDim.copy(alpha = 0.3f)),
        shadowElevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            content = content,
        )
    }
}
