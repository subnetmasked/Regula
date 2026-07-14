package org.regula.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction
import org.regula.app.data.dao.CategoryDao
import org.regula.app.data.dao.CitationDao
import org.regula.app.data.dao.EntryDao
import org.regula.app.data.entity.Category
import org.regula.app.data.entity.Citation
import org.regula.app.data.entity.Entry

@Database(
    entities = [Category::class, Entry::class, Citation::class],
    version = 1,
    exportSchema = false,
)
abstract class RegulaDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun entryDao(): EntryDao
    abstract fun citationDao(): CitationDao

    @Transaction
    suspend fun replaceContent(content: ContentData) {
        citationDao().deleteAll()
        entryDao().deleteAll()
        categoryDao().deleteAll()
        categoryDao().insertAll(content.categories)
        entryDao().insertAll(content.entries)
        citationDao().insertAll(content.citations)
    }

    companion object {
        @Volatile
        private var instance: RegulaDatabase? = null

        fun getInstance(context: Context): RegulaDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    RegulaDatabase::class.java,
                    "regula.db",
                ).build().also { instance = it }
            }
        }
    }
}
