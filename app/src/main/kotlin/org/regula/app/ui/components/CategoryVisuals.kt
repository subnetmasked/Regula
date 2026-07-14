package org.regula.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Balance
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.VolunteerActivism
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import org.regula.app.ui.theme.RegulaCrimson
import org.regula.app.ui.theme.RegulaEmerald
import org.regula.app.ui.theme.RegulaGold
import org.regula.app.ui.theme.RegulaGoldLight
import org.regula.app.ui.theme.RegulaSapphire
import org.regula.app.ui.theme.RegulaSlate

data class CategoryVisual(
    val icon: ImageVector,
    val accent: Color,
)

fun categoryVisual(categoryId: String): CategoryVisual = when (categoryId) {
    "sacraments" -> CategoryVisual(Icons.Outlined.AutoStories, RegulaGoldLight)
    "sexual_ethics" -> CategoryVisual(Icons.Outlined.Favorite, RegulaCrimson)
    "bioethics" -> CategoryVisual(Icons.Outlined.HealthAndSafety, RegulaEmerald)
    "daily_moral" -> CategoryVisual(Icons.Outlined.Balance, RegulaSapphire)
    "social_teaching" -> CategoryVisual(Icons.Outlined.Groups, RegulaGold)
    "prayer" -> CategoryVisual(Icons.Outlined.VolunteerActivism, RegulaGoldLight)
    "ecclesiology" -> CategoryVisual(Icons.Outlined.AccountBalance, RegulaGold)
    "death_afterlife" -> CategoryVisual(Icons.Outlined.NightsStay, RegulaSlate)
    else -> CategoryVisual(Icons.Outlined.AutoStories, RegulaGold)
}
