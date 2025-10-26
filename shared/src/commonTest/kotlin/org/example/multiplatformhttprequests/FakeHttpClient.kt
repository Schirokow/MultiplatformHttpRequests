package org.example.multiplatformhttprequests

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// Diese Klasse simuliert einen HttpClient für Tests
//class FakeHttpClient {
//    // Speichert Listen von Posts für URLs wie "https://jsonplaceholder.typicode.com/posts"
//    private val posts = mutableMapOf<String, List<Post>>()
//
//    // Speichert einen einzelnen Post für URLs wie "https://jsonplaceholder.typicode.com/posts/1"
//    private var singlePost: Post? = null
//
//    // Speichert die Antwort für createPost oder updatePost
//    private var postResponse: Post? = null
//
//    // Speichert einen Fehler, falls wir einen simulieren wollen
//    private var exception: Exception? = null
//
//    // Fügt Test-Posts für eine URL hinzu
//    fun givenPosts(url: String, posts: List<Post>) {
//        this.posts[url] = posts
//    }
//
//    // Fügt einen einzelnen Test-Post hinzu
//    fun givenSinglePost(post: Post) {
//        this.singlePost = post
//    }
//
//    // Fügt eine Test-Antwort für create/update hinzu
//    fun givenPostResponse(post: Post) {
//        this.postResponse = post
//    }
//
//    // Simuliert einen Fehler
//    fun givenException(exception: Exception) {
//        this.exception = exception
//    }
//
//    // Simuliert einen GET-Aufruf
//    suspend fun get(urlString: String, block: HttpRequestBuilder.() -> Unit = {}): HttpResponse {
//        return createResponse(urlString)
//    }
//
//    // Simuliert einen POST-Aufruf
//    suspend fun post(urlString: String, block: HttpRequestBuilder.() -> Unit = {}): HttpResponse {
//        return createResponse(urlString)
//    }
//
//    // Simuliert einen PUT-Aufruf
//    suspend fun put(urlString: String, block: HttpRequestBuilder.() -> Unit = {}): HttpResponse {
//        return createResponse(urlString)
//    }
//
//    // Erstellt eine gefälschte HTTP-Antwort
//    private suspend fun createResponse(urlString: String): HttpResponse {
//        // Wenn ein Fehler simuliert werden soll, werfen wir ihn
//        if (exception != null) throw exception!!
//
//        // Erstelle eine gefälschte Antwort
//        return object : HttpResponse() {
//            override val status get() = HttpStatusCode.OK // Antwort ist immer OK
//            override val content: ByteReadChannel
//                get() = when {
//                    // Für URLs wie "posts/1" geben wir singlePost zurück
//                    urlString.contains("posts/") && singlePost != null -> {
//                        ByteReadChannel(Json.encodeToString(singlePost))
//                    }
//                    // Für URLs wie "posts" geben wir die Liste zurück
//                    urlString.contains("posts") && posts[urlString] != null -> {
//                        ByteReadChannel(Json.encodeToString(posts[urlString]))
//                    }
//                    // Für create/update geben wir postResponse zurück
//                    postResponse != null -> {
//                        ByteReadChannel(Json.encodeToString(postResponse))
//                    }
//                    // Standard: leere Antwort
//                    else -> ByteReadChannel("")
//                }
//            // Diese Felder sind für Tests nicht wichtig
//            override val headers get() = io.ktor.http.Headers.Empty
//            override val requestTime get() = 0L // Einfacher Platzhalter
//            override val responseTime get() = 0L // Einfacher Platzhalter
//            override val version get() = io.ktor.http.HttpProtocolVersion.HTTP_1_1
//            override val call get() = throw NotImplementedError()
//        }
//    }
//}