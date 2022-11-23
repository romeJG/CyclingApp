package com.cometchat.pro.androiduikit.repositories

import com.cometchat.pro.androiduikit.db.Run
import com.cometchat.pro.androiduikit.db.RunDAO
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runDao: RunDAO
){
    suspend fun insertRun(run: Run) =runDao.insertRun(run)

    suspend fun deleteRun(run: Run) =runDao.deleteRun(run)


    //Sort functions
    fun getALLRunsSortedByDate() = runDao.getALLRunsSortedByDate()

    fun getALLRunsSortedByDistance() = runDao.getALLRunsSortedByDistance()

    fun getALLRunsSortedByTimeMillis() = runDao.getALLRunsSortedByTimeMillis()

    fun getALLRunsSortedByAvgSpeed() = runDao.getALLRunsSortedByAvgSpeed()

    fun getALLRunsSortedByCloriesBurned() = runDao.getALLRunsSortedByCloriesBurned()


    //Stats Functions
    fun getTotalAvgSpeed() = runDao.getTotalAvgSpeed()

    fun getTotalDistance() = runDao.getTotalDistance()

    fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()

    fun getTotalTimeInMillis() = runDao.getTotalTimeInMillis()


}