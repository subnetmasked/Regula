package org.regula.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.regula.app.data.RegulaDatabase
import org.regula.app.data.entity.Entry
import org.regula.app.data.model.CategoryWithCount
import org.regula.app.data.model.EntryDetail

class RegulaViewModel(
    database: RegulaDatabase,
) : ViewModel() {
    private val categoryDao = database.categoryDao()
    private val entryDao = database.entryDao()
    private val citationDao = database.citationDao()

    val categoriesWithCount: StateFlow<List<CategoryWithCount>> = categoryDao.getAllWithEntryCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<List<Entry>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                entryDao.getAll().map { emptyList() }
            } else {
                entryDao.searchEntries(query.trim())
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    private val entriesForCategoryCache = mutableMapOf<String, StateFlow<List<Entry>>>()
    private val entryDetailCache = mutableMapOf<String, StateFlow<EntryDetail?>>()

    fun entriesForCategory(categoryId: String): StateFlow<List<Entry>> {
        return entriesForCategoryCache.getOrPut(categoryId) {
            entryDao.getByCategory(categoryId)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = emptyList(),
                )
        }
    }

    fun entryDetail(entryId: String): StateFlow<EntryDetail?> {
        return entryDetailCache.getOrPut(entryId) {
            combine(
                entryDao.getByIdFlow(entryId),
                citationDao.getByEntry(entryId),
            ) { entry, citations ->
                entry?.let {
                    EntryDetail(
                        entry = it,
                        citationsBySource = citations.groupBy { citation -> citation.sourceType },
                    )
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null,
            )
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    class Factory(
        private val database: RegulaDatabase,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegulaViewModel::class.java)) {
                return RegulaViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
