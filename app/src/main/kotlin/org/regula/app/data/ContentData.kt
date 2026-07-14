package org.regula.app.data

import org.regula.app.data.entity.Category
import org.regula.app.data.entity.Citation
import org.regula.app.data.entity.Entry

data class ContentData(
    val contentVersion: Int,
    val categories: List<Category>,
    val entries: List<Entry>,
    val citations: List<Citation>,
)

class ContentLoadException(message: String) : Exception(message)
