package com.dotsdev.idcaller.presentation.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.presentation.template.NormalAppbarActivity

class BlockNotification(val id: Int, val context: Context, val message: Message?) {
    private var notification: NotificationCompat.Builder

    companion object {
        private const val CHANNEL_ID = "block_spam_message_channel"
        private const val CHANNEL_NAME = "Block Spam Message"
        private const val CHANNEL_DESCRIPTION = "Block Message"
        fun newInstance(
            context: Context,
            id: Int,
            message: Message? = null
        ): BlockNotification {
            return BlockNotification(id, context, message)
        }
    }

    init {
        createNotificationChannel()

        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_navigation)
            .setDestination(R.id.mainFlowFragment)
            .setArguments(bundleOf(NormalAppbarActivity.DATA_FROM_KEY to message))
            .createPendingIntent()

        notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_app)
            .setContentTitle("Spam message is blocked")
            .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(pendingIntent)
            .setOngoing(false)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(): Notification {
        val build = notification.build()
        with(NotificationManagerCompat.from(context)) {
            notify(10001, build)
        }
        return build
    }

    fun cancel(id: Int) {
        with(NotificationManagerCompat.from(context)) {
            cancel(id)
        }
    }
}