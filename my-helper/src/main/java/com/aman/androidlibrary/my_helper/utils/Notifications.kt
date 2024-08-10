package com.aman.androidlibrary.my_helper.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.annotation.DrawableRes

object Notifications {
    fun createNotificationChannel(
        context: Context,
        channelId: String,
        channelName: String,
        importance: Int
    ) {
        val channel = NotificationChannel(channelId, channelName, importance)
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    fun checkOrCreateNotificationChannel(
        context: Context,
        channelId: String,
        channelName: String,
        importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val existingChannel = notificationManager.getNotificationChannel(channelId)
        if (existingChannel == null) {
            createNotificationChannel(context, channelId, channelName, importance)
        }
    }

    fun create(
        context: Context,
        channelId: String,
        title: String,
        text: String,
        @DrawableRes icon: Int
    ): Notification {
        return Notification.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(icon)
            .build()
    }
}