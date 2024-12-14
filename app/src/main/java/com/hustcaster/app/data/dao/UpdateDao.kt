package com.hustcaster.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.hustcaster.app.data.model.EpisodeAndUpdate
import com.hustcaster.app.data.model.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UpdateDao {
    @Insert
    suspend fun insertUpdate(update: Update)

    @Delete
    suspend fun deleteUpdate(update: Update)

    @Query("delete from updates")
    suspend fun deleteAllUpdates()

    @Query("select * from updates")
    fun getAllUpdates():Flow<List<Update>>

    @Transaction
    @Query("select * from updates")
    fun getAllEpisodeAndUpdate():Flow<List<EpisodeAndUpdate>>
}