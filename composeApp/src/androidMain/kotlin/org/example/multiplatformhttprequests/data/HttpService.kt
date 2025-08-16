package com.example.bunghttprequests.data

import android.util.Log
//import com.google.android.datatransport.runtime.logging.Logging
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
//import io.ktor.client.plugins.logging.LogLevel
//import io.ktor.client.plugins.logging.Logger
//import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

object HttpService {
    val client = HttpClient(Android){
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
}
//        install(Logging) {
//            logger = object : Logger {
//                override fun log(message: String) {
//                    KMPLogger().log("KtorClient", message) // Verwende den neuen Logger
//                }
//            }
//            level = LogLevel.ALL
//        }
//    }
//}

//object HttpService {
//    val client = HttpClient(Android) {
//        install(ContentNegotiation) {
//            json(Json {
//                prettyPrint = true
//                isLenient = true
//                ignoreUnknownKeys = true
//            })
//        }
//        install(Logging) {
//            logger = object : Logger {
//                override fun log(message: String) {
//                    Log.i("KtorClient", message) // Ktor-Ausgaben in Logcat schreiben
//                }
//            }
//            level = LogLevel.ALL // Alle Details protokollieren
//        }
//
//    }
//}