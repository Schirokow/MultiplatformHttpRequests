package org.example.multiplatformhttprequests

import android.util.Log

actual fun logMessage(message: String) {
    Log.d("HttpService", message)
}