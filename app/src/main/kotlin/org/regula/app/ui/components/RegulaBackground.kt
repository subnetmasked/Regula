package org.regula.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import org.regula.app.ui.theme.RegulaBlack
import org.regula.app.ui.theme.RegulaDeep
import org.regula.app.ui.theme.RegulaGold
import org.regula.app.ui.theme.RegulaGoldDim

@Composable
fun RegulaBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        RegulaDeep,
                        RegulaBlack,
                        Color(0xFF050506),
                    ),
                ),
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            RegulaGold.copy(alpha = 0.14f),
                            RegulaGoldDim.copy(alpha = 0.04f),
                            Color.Transparent,
                        ),
                        center = Offset(0.5f, 0.08f),
                        radius = 900f,
                    ),
                ),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            RegulaGold.copy(alpha = 0.06f),
                            Color.Transparent,
                        ),
                        center = Offset(0.85f, 0.75f),
                        radius = 500f,
                    ),
                ),
        )
        content()
    }
}
