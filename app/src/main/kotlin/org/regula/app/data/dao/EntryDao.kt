package org.regula.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.regula.app.data.entity.Entry

@Dao
interface EntryDao {
    @Query("SELECT * FROM entries ORDER BY question ASC")
    fun getAll(): Flow<List<Entry>>

    @Query("SELECT * FROM entries WHERE id = :id")
    suspend fun getById(id: String): Entry?

    @Query("SELECT * FROM entries WHERE id = :id")
    fun getByIdFlow(id: String): Flow<Entry?>

    @Query("SELECT * FROM entries WHERE categoryId = :categoryId ORDER BY question ASC")
    fun getByCategory(categoryId: String): Flow<List<Entry>>

    @Query(
        """
        SELECT * FROM entries
        WHERE question LIKE '%' || :query || '%'
           OR shortAnswer LIKE '%' || :query || '%'
        ORDER BY question ASC
        """,
    )
    fun searchEntries(query: String): Flow<List<Entry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<Entry>)

    @Query("DELETE FROM entries")
    suspend fun deleteAll()
}
