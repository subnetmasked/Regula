package org.regula.app.data.model

import org.regula.app.data.entity.Citation
import org.regula.app.data.entity.Entry

data class EntryDetail(
    val entry: Entry,
    val citationsBySource: Map<String, List<Citation>>,
)
