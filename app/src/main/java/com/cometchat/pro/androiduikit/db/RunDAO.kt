package com.cometchat.pro.androiduikit.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RunDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: Run)

    @Delete
    suspend fun deleteRun(run: Run)

    @Query("SELECT * FROM cycling_table ORDER BY timestamp DESC")
    fun getALLRunsSortedByDate(): LiveData<List<Run>>

    @Query("SELECT * FROM cycling_table ORDER BY timeInMillis DESC")
    fun getALLRunsSortedByTimeMillis(): LiveData<List<Run>>

    @Query("SELECT * FROM cycling_table ORDER BY caloriesBurned DESC")
    fun getALLRunsSortedByCloriesBurned(): LiveData<List<Run>>

    @Query("SELECT * FROM cycling_table ORDER BY avgSpeedInKMH DESC")
    fun getALLRunsSortedByAvgSpeed(): LiveData<List<Run>>

    @Query("SELECT * FROM cycling_table ORDER BY distanceInMeters DESC")
    fun getALLRunsSortedByDistance(): LiveData<List<Run>>

    @Query("SELECT SUM(timeInMillis) FROM cycling_table")
    fun getTotalTimeInMillis(): LiveData<Long>

    @Query("SELECT SUM(caloriesBurned) FROM cycling_table")
    fun getTotalCaloriesBurned(): LiveData<Int>

    @Query("SELECT SUM(distanceInMeters) FROM cycling_table")
    fun getTotalDistance(): LiveData<Int>

    @Query("SELECT AVG(avgSpeedInKMH) FROM cycling_table")
    fun getTotalAvgSpeed(): LiveData<Float>
}