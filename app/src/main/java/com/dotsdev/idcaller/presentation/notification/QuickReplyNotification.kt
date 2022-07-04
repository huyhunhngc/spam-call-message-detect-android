package com.dotsdev.idcaller.presentation.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_SOUND
import androidx.core.app.NotificationCompat.DEFAULT_VIBRATE
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.data.model.toMessageGroup
import com.dotsdev.idcaller.presentation.template.NormalAppbarActivity.Companion.DATA_FROM_KEY

class QuickReplyNotification(val id: Int, val context: Context, val message: Message?) {
    private var notification: NotificationCompat.Builder

    companion object {
        private const val CHANNEL_ID = "receive_message_channel"
        private const val CHANNEL_NAME = "Receive Message"
        private const val CHANNEL_DESCRIPTION = "Float Message"
        private const val KEY_TEXT_REPLY = "key_text_reply"
        fun newInstance(
            context: Context,
            id: Int,
            message: Message? = null
        ): QuickReplyNotification {
            return QuickReplyNotification(id, context, message)
        }
    }

    init {
        createNotificationChannel()
        val replyLabel: String = context.resources.getString(R.string.reply_label)
        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(replyLabel)
            build()
        }

        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_navigation)
            .setDestination(R.id.mainFlowFragment)
            .setArguments(bundleOf(DATA_FROM_KEY to listOf(message).mapNotNull { it }.toMessageGroup()))
            .createPendingIntent()

        val action: NotificationCompat.Action =
            NotificationCompat.Action.Builder(
                R.drawable.ic_baseline_send,
                context.getString(R.string.reply_label), pendingIntent
            ).addRemoteInput(remoteInput).build()
        notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_app)
            .setContentTitle(message?.messageName)
            .setContentText(message?.content)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message?.content)
            )
            .addAction(action)
            .setDefaults(DEFAULT_SOUND or DEFAULT_VIBRATE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
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
            notify(id, build)
        }
        return build
    }

    fun cancel(id: Int) {
        with(NotificationManagerCompat.from(context)) {
            cancel(id)
        }
    }
}
