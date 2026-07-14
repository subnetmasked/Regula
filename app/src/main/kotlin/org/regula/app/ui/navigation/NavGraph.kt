package org.regula.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.regula.app.ui.RegulaViewModel
import org.regula.app.ui.screens.CategoryDetailScreen
import org.regula.app.ui.screens.EntryDetailScreen
import org.regula.app.ui.screens.WelcomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: RegulaViewModel,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME,
        modifier = modifier,
    ) {
        composable(Routes.WELCOME) {
            WelcomeScreen(
                viewModel = viewModel,
                onCategoryClick = { categoryId ->
                    navController.navigate(Routes.category(categoryId))
                },
                onEntryClick = { entryId ->
                    navController.navigate(Routes.entry(entryId))
                },
            )
        }

        composable(
            route = Routes.CATEGORY,
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: return@composable
            CategoryDetailScreen(
                categoryId = categoryId,
                viewModel = viewModel,
                onEntryClick = { entryId ->
                    navController.navigate(Routes.entry(entryId))
                },
                onBack = { navController.popBackStack() },
            )
        }

        composable(
            route = Routes.ENTRY,
            arguments = listOf(navArgument("entryId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getString("entryId") ?: return@composable
            EntryDetailScreen(
                entryId = entryId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
