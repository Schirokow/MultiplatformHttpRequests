package org.example.multiplatformhttprequests

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

actual val httpClient: HttpClient = HttpClient(Android) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                logMessage("HTTP: $message")
            }
        }
        level = LogLevel.ALL
    }
    engine {
        connectTimeout = 100_000
        socketTimeout = 100_000
    }
}

