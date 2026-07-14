package org.regula.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.regula.app.R
import org.regula.app.data.model.CategoryWithCount
import org.regula.app.ui.RegulaViewModel
import org.regula.app.ui.components.CategoryIconBadge
import org.regula.app.ui.components.EntryResultCard
import org.regula.app.ui.components.FancySearchBar
import org.regula.app.ui.components.GlassCard
import org.regula.app.ui.components.OrnamentalTitle
import org.regula.app.ui.components.SectionHeader
import org.regula.app.ui.components.categoryVisual

@Composable
fun WelcomeScreen(
    viewModel: RegulaViewModel,
    onCategoryClick: (String) -> Unit,
    onEntryClick: (String) -> Unit,
) {
    val categories by viewModel.categoriesWithCount.collectAsStateWithLifecycle()
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val isSearching = query.isNotBlank()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 28.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        item {
            OrnamentalTitle(
                title = stringResource(R.string.app_name).uppercase(),
                subtitle = stringResource(R.string.welcome_subtitle),
            )
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
            FancySearchBar(
                query = query,
                onQueryChange = viewModel::updateSearchQuery,
            )
        }

        item {
            AnimatedVisibility(
                visible = isSearching,
                enter = fadeIn() + slideInVertically { it / 4 },
                exit = fadeOut(),
            ) {
                Column(
                    modifier = Modifier.padding(top = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    SectionHeader(
                        title = stringResource(R.string.search_results),
                        subtitle = when {
                            searchResults.isEmpty() -> stringResource(R.string.no_results)
                            else -> stringResource(R.string.results_count, searchResults.size)
                        },
                    )
                    searchResults.forEach { entry ->
                        EntryResultCard(
                            entry = entry,
                            onClick = { onEntryClick(entry.id) },
                        )
                    }
                }
            }
        }

        item {
            AnimatedVisibility(
                visible = !isSearching,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(32.dp))
                    SectionHeader(
                        title = stringResource(R.string.explore_topics),
                        subtitle = stringResource(R.string.explore_subtitle),
                    )
                }
            }
        }

        if (!isSearching) {
            if (categories.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.loading),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 24.dp),
                    )
                }
            } else {
                items(categories.chunked(2), key = { row -> row.first().categoryId }) { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        row.forEach { category ->
                            WelcomeCategoryCard(
                                category = category,
                                onClick = { onCategoryClick(category.categoryId) },
                                modifier = Modifier.weight(1f),
                            )
                        }
                        if (row.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    GlassCard(onClick = null) {
                        Text(
                            text = stringResource(R.string.welcome_footer),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WelcomeCategoryCard(
    category: CategoryWithCount,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val visual = categoryVisual(category.categoryId)

    GlassCard(
        onClick = onClick,
        modifier = modifier,
        accentColor = visual.accent,
    ) {
        CategoryIconBadge(icon = visual.icon, tint = visual.accent)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = category.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.entry_count, category.entryCount),
                style = MaterialTheme.typography.labelMedium,
                color = visual.accent.copy(alpha = 0.85f),
            )
        }
    }
}
