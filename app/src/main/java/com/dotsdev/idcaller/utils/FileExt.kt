package com.dotsdev.idcaller.utils

import android.util.Log
import java.io.IOException
import java.io.InputStream

fun InputStream.toStringValue(): String? {
    return try {
        val bytes = ByteArray(this.available())
        this.read(bytes, 0, bytes.size)
        Log.d("InputStream", "toStringValue: ${String(bytes)}")
        String(bytes)
    } catch (e: IOException) {
        null
    }
}
