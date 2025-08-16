package org.example.multiplatformhttprequests

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText

expect val httpClient: HttpClient

expect fun logMessage(message: String) // Plattformspezifisches Logging

object HttpService {
    val client = httpClient

}
