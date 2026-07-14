package org.regula.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.regula.app.data.entity.Category
import org.regula.app.data.model.CategoryWithCount

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY sortOrder ASC")
    fun getAll(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getById(id: String): Category?

    @Query(
        """
        SELECT categories.id AS categoryId,
               categories.name AS name,
               categories.sortOrder AS sortOrder,
               COUNT(entries.id) AS entryCount
        FROM categories
        LEFT JOIN entries ON entries.categoryId = categories.id
        GROUP BY categories.id
        ORDER BY categories.sortOrder ASC
        """,
    )
    fun getAllWithEntryCount(): Flow<List<CategoryWithCount>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)

    @Query("DELETE FROM categories")
    suspend fun deleteAll()
}
