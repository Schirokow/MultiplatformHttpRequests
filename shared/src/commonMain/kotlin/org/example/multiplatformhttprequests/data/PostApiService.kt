package org.example.multiplatformhttprequests.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import org.example.multiplatformhttprequests.logMessage


@Serializable
data class Post(
    val userId: Int,
    val id: Int? = null,
    val title: String,
    val body: String
)


class PostApiService(private val client: HttpClient) {


    suspend fun getPosts(): List<Post> {
        return try {
            withContext(Dispatchers.IO) {
                client.get("https://jsonplaceholder.typicode.com/posts") {
                    contentType(ContentType.Application.Json)
                }.body<List<Post>>()
            }
        } catch (e: Exception) {
            logMessage("Fehler beim Laden der Posts: ${e.message}")
            emptyList()
        }
    }

    suspend fun getPostsByUserId(userId: Int? = null): List<Post> {
        return try {
            withContext(Dispatchers.IO) {
                client.get("https://jsonplaceholder.typicode.com/posts?userId=${userId}") {
                    contentType(ContentType.Application.Json)
                }.body<List<Post>>()
            }
        } catch (e: Exception) {
            logMessage("Fehler beim Laden der Posts nach userId: ${e.message}")
            emptyList()
        }
    }


    suspend fun getPostById(id: Int?): Post? {
        return try {
            withContext(Dispatchers.IO) {
                client.get("https://jsonplaceholder.typicode.com/posts/${id}") {
                    contentType(ContentType.Application.Json)
                }.body<Post>()
            }
        } catch (e: Exception) {
            logMessage("Fehler beim Laden des Posts nach Id: ${e.message}")
            null
        }
    }

    suspend fun createPost(newPost: Post): Post? {
        return try {
            withContext(Dispatchers.IO) {
                client.post("https://jsonplaceholder.typicode.com/posts") {
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    setBody(newPost)
                }.body<Post>()
            }
        } catch (e: Exception) {
            logMessage("Fehler beim Erstellen des Posts: ${e.message}")
            null
        }
    }

    suspend fun updatePost(post: Post): Post? {
        return try {
            withContext(Dispatchers.IO) {
                client.put("https://jsonplaceholder.typicode.com/posts/${post.id}") {
                    contentType(ContentType.Application.Json)
                    setBody(post)
                }.body<Post>()
            }
        } catch (e: Exception) {
            logMessage("Fehler beim Aktualisieren des Posts: ${e.message}")
            null
        }
    }
}



