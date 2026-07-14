package org.regula.app.data.model

import androidx.room.ColumnInfo

data class CategoryWithCount(
    @ColumnInfo(name = "categoryId") val categoryId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "sortOrder") val sortOrder: Int,
    @ColumnInfo(name = "entryCount") val entryCount: Int,
)
