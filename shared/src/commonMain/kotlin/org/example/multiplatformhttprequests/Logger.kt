package org.example.multiplatformhttprequests

expect class KMPLogger() {
    fun log(tag: String, message: String)
}