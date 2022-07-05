package com.dotsdev.idcaller.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri

fun Context.saveReceivedSms(address: String, body: String) {
    val values = ContentValues().apply {
        put("address", address)
        put("body", body)
    }
    contentResolver.insert(Uri.parse("content://sms/inbox"), values)
}