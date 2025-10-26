package org.example.multiplatformhttprequests

import io.ktor.client.HttpClient



val client = httpClient



expect val httpClient: HttpClient

expect fun logMessage(message: String) // Plattformspezifisches Logging

