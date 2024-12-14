package com.hustcaster.app.data.repository

import com.hustcaster.app.data.dao.UpdateDao
import com.hustcaster.app.data.model.Update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateRepository @Inject constructor(
    private val updateDao: UpdateDao
) {
    suspend fun insertUpdate(update:Update)=updateDao.insertUpdate(update)

    suspend fun insertAllUpdates(updates:List<Update>)=updateDao.insertAllUpdates(updates)

    suspend fun deleteUpdate(update: Update)=updateDao.deleteUpdate(update)

    suspend fun deleteAllUpdates()=updateDao.deleteAllUpdates()

    fun getAllUpdates()=updateDao.getAllUpdates()

    fun getAllEpisodeAndUpdates()=updateDao.getAllEpisodeAndUpdate()

    companion object{
        @Volatile
        private var instance:UpdateRepository?=null

        fun getInstance(updateDao: UpdateDao)=
            instance?: synchronized(this){
                instance?:UpdateRepository(updateDao).also { instance=it }
            }
    }
}