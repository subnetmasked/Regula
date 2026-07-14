package org.regula.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val RegulaColorScheme = darkColorScheme(
    primary = RegulaGold,
    onPrimary = RegulaBlack,
    primaryContainer = RegulaGoldDim,
    onPrimaryContainer = RegulaIvory,
    secondary = RegulaGoldLight,
    onSecondary = RegulaBlack,
    secondaryContainer = RegulaSurfaceElevated,
    onSecondaryContainer = RegulaGoldLight,
    tertiary = RegulaGoldDim,
    onTertiary = RegulaIvory,
    background = RegulaBlack,
    onBackground = RegulaIvory,
    surface = RegulaSurface,
    onSurface = RegulaIvory,
    surfaceVariant = RegulaSurfaceElevated,
    onSurfaceVariant = RegulaIvoryDim,
    outline = RegulaGoldMuted,
    outlineVariant = RegulaGoldDim.copy(alpha = 0.35f),
    inverseSurface = RegulaIvory,
    inverseOnSurface = RegulaBlack,
    error = RegulaCrimson,
    onError = RegulaIvory,
)

fun classificationColor(classification: String): Color = when (classification) {
    "dogma" -> RegulaCrimson
    "doctrine" -> RegulaSapphire
    "discipline" -> RegulaEmerald
    else -> RegulaSlate
}

fun classificationLabel(classification: String): String = when (classification) {
    "dogma" -> "Dogma"
    "doctrine" -> "Doctrine"
    "discipline" -> "Discipline"
    "theological_opinion" -> "Theological Opinion"
    else -> classification.replaceFirstChar { it.uppercase() }
}

fun sourceTypeLabel(sourceType: String): String = when (sourceType) {
    "ccc" -> "Catechism of the Catholic Church"
    "canon_law" -> "Canon Law"
    "aquinas" -> "St. Thomas Aquinas"
    "magisterial" -> "Magisterial Document"
    "scripture" -> "Sacred Scripture"
    else -> sourceType.replaceFirstChar { it.uppercase() }
}

@Composable
fun RegulaTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = RegulaColorScheme,
        typography = RegulaTypography,
        shapes = RegulaShapes,
        content = content,
    )
}
