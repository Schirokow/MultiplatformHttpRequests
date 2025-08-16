package org.example.multiplatformhttprequests

actual class KMPLogger {
    actual fun log(tag: String, message: String) {
        println("[$tag] $message")
    }
}