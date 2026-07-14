package org.regula.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import org.regula.app.data.entity.Citation
import org.regula.app.ui.RegulaViewModel
import org.regula.app.ui.components.CitationCardContent
import org.regula.app.ui.components.ClassificationBadge
import org.regula.app.ui.components.GlassCard
import org.regula.app.ui.components.RegulaTopBar
import org.regula.app.ui.components.SectionHeader
import org.regula.app.ui.components.SolidCard
import org.regula.app.ui.theme.sourceTypeLabel

@Composable
fun EntryDetailScreen(
    entryId: String,
    viewModel: RegulaViewModel,
    onBack: () -> Unit,
) {
    val detail by viewModel.entryDetail(entryId).collectAsStateWithLifecycle()

    val entryDetail = detail
    if (entryDetail == null) {
        Text(
            text = stringResource(R.string.loading),
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(24.dp),
            style = MaterialTheme.typography.bodyLarge,
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 36.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                RegulaTopBar(
                    title = stringResource(R.string.entry_detail),
                    onBack = onBack,
                )
            }

            item {
                GlassCard(onClick = null) {
                    Text(
                        text = entryDetail.entry.question,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    ClassificationBadge(classification = entryDetail.entry.classification)
                }
            }

            item {
                SolidCard(onClick = null) {
                    SectionHeader(title = stringResource(R.string.answer))
                    Text(
                        text = entryDetail.entry.shortAnswer,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }

            item {
                SolidCard(onClick = null) {
                    SectionHeader(title = stringResource(R.string.classification_note))
                    Text(
                        text = entryDetail.entry.classificationNote,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            item {
                SectionHeader(
                    title = stringResource(R.string.citations),
                    subtitle = stringResource(R.string.citations_subtitle),
                )
            }

            entryDetail.citationsBySource.forEach { (sourceType, citations) ->
                item(key = "header_$sourceType") {
                    Text(
                        text = sourceTypeLabel(sourceType).uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                    )
                }
                items(citations, key = { it.id }) { citation ->
                    CitationCard(citation = citation)
                }
            }
        }
    }
}

@Composable
private fun CitationCard(citation: Citation) {
    GlassCard(onClick = null) {
        CitationCardContent(
            reference = citation.reference,
            summary = citation.summary,
        )
    }
}
