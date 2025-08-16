package org.example.multiplatformhttprequests

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform