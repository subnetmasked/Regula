package org.regula.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.regula.app.data.entity.Citation

@Dao
interface CitationDao {
    @Query("SELECT * FROM citations ORDER BY id ASC")
    fun getAll(): Flow<List<Citation>>

    @Query("SELECT * FROM citations WHERE id = :id")
    suspend fun getById(id: Long): Citation?

    @Query("SELECT * FROM citations WHERE entryId = :entryId ORDER BY sourceType ASC, reference ASC")
    fun getByEntry(entryId: String): Flow<List<Citation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(citations: List<Citation>)

    @Query("DELETE FROM citations")
    suspend fun deleteAll()
}
