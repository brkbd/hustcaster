package com.hustcaster.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.hustcaster.app.data.model.EpisodeAndRecord
import com.hustcaster.app.data.model.Record
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert
    suspend fun insertRecord(record: Record)

    @Delete
    suspend fun deleteRecord(record: Record)

    @Query("delete from records")
    suspend fun deleteAllRecords()

    @Query("select * from records where episode_id=:id")
    fun getRecordByEpisodeId(id: Long): List<Record>


    @Query("select * from records")
    fun getRecords(): List<Record>

    @Query("delete from records where episode_id=:id")
    suspend fun deleteRecordById(id: Long)

    @Transaction
    @Query("select * from records")
    fun getEpisodeAndRecordListFlow(): Flow<List<EpisodeAndRecord>>

    @Query("select * from records order by record_id desc limit 3")
    fun getThreeLatestRecords(): List<EpisodeAndRecord>

    @Query("select image_url from podcasts where id=(select podcast_id from episodes where id=(select episode_id from records where id=:id))")
    fun getImageUrlByRecordId(id:Long):List<String>

    @Query("select * from records where episode_id=:id")
    fun getEpisodeAndRecordById(id: Long): Flow<EpisodeAndRecord>

}