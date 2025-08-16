package org.example.multiplatformhttprequests.data.dao

//expect fun createHttpClient(): HttpClient
//
//object HttpService {
//    val client = createHttpClient {
//        install(ContentNegotiation) {
//            json(Json {
//                prettyPrint = true
//                isLenient = true
//                ignoreUnknownKeys = true
//            })
//        }
//    }
//}