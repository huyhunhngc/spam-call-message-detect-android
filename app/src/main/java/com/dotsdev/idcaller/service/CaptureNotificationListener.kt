package com.dotsdev.idcaller.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.text.TextUtils
import android.util.Log

class CaptureNotificationListener : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        if (!TextUtils.isEmpty(packageName)) {
            Log.d("!@#", "onNotificationPosted: ${sbn.notification}")
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        if (!TextUtils.isEmpty(packageName)) {
            Log.d(
                "!@#",
                "onNotificationRemoved ${sbn.notification.tickerText}" +
                    "${sbn.notification.category}"
            )
        }
    }
}
