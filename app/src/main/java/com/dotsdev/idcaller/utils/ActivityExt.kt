package com.dotsdev.idcaller.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context


fun Activity.isMyServiceRunning(serviceClass: Class<*>): Boolean {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
    manager?.getRunningServices(Int.MAX_VALUE)?.forEach { service ->
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}