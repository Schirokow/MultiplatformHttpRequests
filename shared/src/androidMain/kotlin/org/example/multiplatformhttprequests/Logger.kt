package org.example.multiplatformhttprequests
import android.util.Log

actual class KMPLogger {
    actual fun log(tag: String, message: String) {
        Log.i(tag, message)
    }
}