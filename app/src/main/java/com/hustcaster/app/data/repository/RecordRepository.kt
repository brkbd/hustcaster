package com.hustcaster.app.data.repository

import com.hustcaster.app.data.dao.RecordDao
import com.hustcaster.app.data.model.EpisodeAndRecord
import com.hustcaster.app.data.model.Record
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecordRepository @Inject constructor(
    private val recordDao: RecordDao
) {
    suspend fun insertRecord(record: Record) = recordDao.insertRecord(record)

    suspend fun deleteRecord(record: Record) = recordDao.deleteRecord(record)

    suspend fun deleteAllRecords() = recordDao.deleteAllRecords()

    fun getRecordByEpisodeId(id: Long) = recordDao.getRecordByEpisodeId(id)

    fun getRecords() = recordDao.getRecords()

    suspend fun deleteRecordById(id: Long) = recordDao.deleteRecordById(id)

    fun getEpisodeAndRecordListFlow() = recordDao.getEpisodeAndRecordListFlow()

    fun getEpisodeAndRecordById(id: Long) = recordDao.getEpisodeAndRecordById(id)

    fun getThreeLatestRecords() = recordDao.getThreeLatestRecords()

    fun getLatestRecord() = recordDao.getEpisodeAndRecordListFlow().mapNotNull {
        it.last()
    }

    fun getImageUrlOfRecord(id: Long) = recordDao.getImageUrlByRecordId(id)

    fun getImageUrlsOfRecords(records: List<EpisodeAndRecord>): Flow<List<String>> {
        val imageUrls = mutableListOf<String>()
        records.forEach { record ->
            imageUrls.add(getImageUrlOfRecord(record.record.id)[0])
        }
        return flow { emit(imageUrls) }
    }


    companion object {
        @Volatile
        private var instance: RecordRepository? = null

        fun getInstance(recordDao: RecordDao) =
            instance ?: synchronized(this) {
                instance ?: RecordRepository(recordDao).also { instance = it }
            }

    }
}