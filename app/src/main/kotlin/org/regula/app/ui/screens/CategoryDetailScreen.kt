package org.regula.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.regula.app.R
import org.regula.app.ui.RegulaViewModel
import org.regula.app.ui.components.EntryResultCard
import org.regula.app.ui.components.RegulaTopBar
import org.regula.app.ui.components.categoryVisual

@Composable
fun CategoryDetailScreen(
    categoryId: String,
    viewModel: RegulaViewModel,
    onEntryClick: (String) -> Unit,
    onBack: () -> Unit,
) {
    val entries by viewModel.entriesForCategory(categoryId).collectAsStateWithLifecycle()
    val categories by viewModel.categoriesWithCount.collectAsStateWithLifecycle()
    val category = categories.find { it.categoryId == categoryId }
    val categoryName = category?.name ?: stringResource(R.string.category)
    val visual = categoryVisual(categoryId)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            RegulaTopBar(
                title = categoryName,
                subtitle = stringResource(R.string.entry_count, category?.entryCount ?: entries.size),
                onBack = onBack,
            )
        }

        items(entries, key = { it.id }) { entry ->
            EntryResultCard(
                entry = entry,
                onClick = { onEntryClick(entry.id) },
                showAnswer = false,
            )
        }

        if (entries.isEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.loading),
                    style = MaterialTheme.typography.bodyLarge,
                    color = visual.accent.copy(alpha = 0.8f),
                )
            }
        }
    }
}
