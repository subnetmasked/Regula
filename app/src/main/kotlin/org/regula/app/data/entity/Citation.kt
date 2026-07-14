package org.regula.app.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "citations",
    foreignKeys = [
        ForeignKey(
            entity = Entry::class,
            parentColumns = ["id"],
            childColumns = ["entryId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("entryId")],
)
data class Citation(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val entryId: String,
    val sourceType: String,
    val reference: String,
    val summary: String,
)
