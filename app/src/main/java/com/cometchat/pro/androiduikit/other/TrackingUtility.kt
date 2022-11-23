package com.cometchat.pro.androiduikit.other

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Build
import com.cometchat.pro.androiduikit.services.Polyline
import pub.devrel.easypermissions.EasyPermissions
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.concurrent.TimeUnit

object TrackingUtility {

    fun hasLocationPermissions(context: Context) =
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }else{
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }

    fun calculatePolylineLength(polyline: Polyline): Float{
        var distance = 0f
        for(i in 0..polyline.size - 2){
            val pos1 = polyline[i]
            val pos2 = polyline[i + 1]
            val result = FloatArray(1)
            Location.distanceBetween(
                pos1.latitude,
                pos1.longitude,
                pos2.latitude,
                pos2.longitude,
                result
            )
            distance += result[0]
        }
        return  distance
    }

    fun getFormatedStopWatchTime(ms: Long, includeMillis: Boolean = false): String{
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        if (!includeMillis){
            val f: NumberFormat = DecimalFormat("00")
            return "${f.format(hours)}:${f.format(minutes)}:${f.format(seconds)}"
        }
        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= 10
        val f: NumberFormat = DecimalFormat("00")
        return "${f.format(hours)}:${f.format(minutes)}:${f.format(seconds)}:${f.format(milliseconds)}"
    }
}