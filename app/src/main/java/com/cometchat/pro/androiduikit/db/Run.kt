package com.cometchat.pro.androiduikit.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "cycling_table")
data class Run(
    var img: Bitmap? = null,
    var timestamp: Long = 0L,
    var avgSpeedInKMH: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long= 0L,
    var caloriesBurned: Int = 0
//uses miliseconds for more accuracy in time
//use functions to convert them to time we need
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}

