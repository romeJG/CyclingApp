package com.cometchat.pro.androiduikit

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.cometchat.pro.androiduikit.constants.AppConfig
import com.cometchat.pro.core.AppSettings.AppSettingsBuilder
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.core.CometChat.CallbackListener
import com.cometchat.pro.core.CometChat.addConnectionListener
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.uikit.ui_components.calls.call_manager.listener.CometChatCallListener.addCallListener
import com.cometchat.pro.uikit.ui_components.calls.call_manager.listener.CometChatCallListener.removeCallListener
import com.cometchat.pro.uikit.ui_settings.UIKitSettings
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UIKitApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val appSettings = AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(AppConfig.AppDetails.REGION).build()
        CometChat.init(this, AppConfig.AppDetails.APP_ID, appSettings, object : CallbackListener<String>() {
            override fun onSuccess(s: String) {
                UIKitSettings.setAppID(AppConfig.AppDetails.APP_ID)
                UIKitSettings.setAuthKey(AppConfig.AppDetails.AUTH_KEY)
                CometChat.setSource("ui-kit", "android", "kotlin")
                Log.d(TAG, "onSuccess: $s")
            }

            override fun onError(e: CometChatException) {
            }
        })
        val uiKitSettings = UIKitSettings(this)
        uiKitSettings.addConnectionListener(TAG)
        addCallListener(TAG, this)
        createNotificationChannel()
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(R.string.app_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("2", name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        removeCallListener(TAG)
        CometChat.removeConnectionListener(TAG)
    }

    companion object {
        private const val TAG = "UIKitApplication"
    }
}