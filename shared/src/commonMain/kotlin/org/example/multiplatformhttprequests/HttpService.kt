package org.example.multiplatformhttprequests

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText

expect val httpClient: HttpClient

expect fun logMessage(message: String) // Plattformspezifisches Logging

object HttpService {
    val client = httpClient

    suspend fun getData(url: String): String {
        return try {
            val response: HttpResponse = client.get(url)
            response.bodyAsText()
        } catch (e: Exception) {
            logMessage("Error fetching data: ${e.message}")
            ""
        }
    }
}
