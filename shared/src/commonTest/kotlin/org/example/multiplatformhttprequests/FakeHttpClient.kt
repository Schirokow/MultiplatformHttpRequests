package org.example.multiplatformhttprequests

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpProtocolVersion
import io.ktor.http.Headers
import io.ktor.util.date.GMTDate
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.InternalAPI
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.multiplatformhttprequests.data.Post
import kotlin.coroutines.CoroutineContext

class FakeHttpClient {
    private val posts = mutableMapOf<String, List<Post>>()
    private var singlePost: Post? = null
    private var postResponse: Post? = null
    private var exception: Exception? = null

    fun givenPosts(url: String, posts: List<Post>) {
        this.posts[url] = posts
    }

    fun givenSinglePost(post: Post) {
        this.singlePost = post
    }

    fun givenPostResponse(post: Post) {
        this.postResponse = post
    }

    fun givenException(exception: Exception) {
        this.exception = exception
    }

    suspend fun get(urlString: String, block: HttpRequestBuilder.() -> Unit = {}): HttpResponse {
        return createResponse(urlString)
    }

    suspend fun post(urlString: String, block: HttpRequestBuilder.() -> Unit = {}): HttpResponse {
        return createResponse(urlString)
    }

    suspend fun put(urlString: String, block: HttpRequestBuilder.() -> Unit = {}): HttpResponse {
        return createResponse(urlString)
    }

    private suspend fun createResponse(urlString: String): HttpResponse {
        if (exception != null) throw exception!!

        return object : HttpResponse() {
            override val coroutineContext: CoroutineContext get() = object : CoroutineContext {
                override fun <R> fold(initial: R, operation: (R, CoroutineContext.Element) -> R): R = initial
                override fun <E : CoroutineContext.Element> get(key: CoroutineContext.Key<E>): E? = null
                override fun minusKey(key: CoroutineContext.Key<*>): CoroutineContext = this
                override fun plus(context: CoroutineContext): CoroutineContext = context
            }
            override val status: HttpStatusCode get() = HttpStatusCode.OK
            @OptIn(InternalAPI::class)
            override val rawContent: ByteReadChannel get() = when {
                urlString.contains("posts/") && singlePost != null -> {
                    ByteReadChannel(Json.encodeToString(singlePost))
                }
                urlString.contains("posts") && posts[urlString] != null -> {
                    ByteReadChannel(Json.encodeToString(posts[urlString]))
                }
                postResponse != null -> {
                    ByteReadChannel(Json.encodeToString(postResponse))
                }
                else -> ByteReadChannel("")
            }
            override val headers: Headers get() = Headers.Empty
            override val requestTime: GMTDate get() = GMTDate(0)
            override val responseTime: GMTDate get() = GMTDate(0)
            override val version: HttpProtocolVersion get() = HttpProtocolVersion.HTTP_1_1
            override val call get() = throw NotImplementedError()
        }
    }
}