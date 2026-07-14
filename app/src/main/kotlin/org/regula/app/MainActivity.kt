package org.regula.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import org.regula.app.ui.RegulaViewModel
import org.regula.app.ui.components.RegulaBackground
import org.regula.app.ui.navigation.NavGraph
import org.regula.app.ui.theme.RegulaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }

        val application = application as RegulaApplication

        setContent {
            RegulaTheme {
                RegulaBackground(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    val viewModel: RegulaViewModel = viewModel(
                        factory = RegulaViewModel.Factory(application.database),
                    )
                    NavGraph(
                        navController = navController,
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}
