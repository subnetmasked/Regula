package org.regula.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.regula.app.ui.theme.RegulaGold
import org.regula.app.ui.theme.RegulaGoldDim
import org.regula.app.ui.theme.RegulaGoldMuted

@Composable
fun GoldDivider(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        RegulaGoldMuted,
                        RegulaGold,
                        RegulaGoldDim,
                        RegulaGoldMuted,
                    ),
                ),
            ),
    )
}

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        modifier = modifier.padding(bottom = if (subtitle != null) 4.dp else 8.dp),
    )
    subtitle?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 12.dp),
        )
    }
    GoldDivider(modifier = Modifier.padding(bottom = 16.dp))
}

@Composable
fun OrnamentalTitle(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.displayLarge,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
    GoldDivider(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth(0.35f),
    )
    Text(
        text = subtitle,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}
