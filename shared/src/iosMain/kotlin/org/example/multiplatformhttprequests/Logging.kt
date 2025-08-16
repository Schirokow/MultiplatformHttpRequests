package org.example.multiplatformhttprequests

import platform.Foundation.NSLog

actual fun logMessage(message: String) {
    NSLog("HttpService: %s", message)
}