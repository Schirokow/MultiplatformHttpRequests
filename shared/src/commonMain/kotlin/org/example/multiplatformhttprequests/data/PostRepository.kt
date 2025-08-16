package org.example.multiplatformhttprequests.data

import org.example.multiplatformhttprequests.HttpService.client
import org.example.multiplatformhttprequests.data.PostRepository.getPostById
import org.example.multiplatformhttprequests.data.PostRepository.getPosts
import org.example.multiplatformhttprequests.data.PostRepository.getPostsByUserId
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import org.example.multiplatformhttprequests.logMessage

object PostRepository {
    @Serializable
    data class Post(
        val userId: Int,
        val id: Int? = null,
        val title: String,
        val body: String
    )

    suspend fun getPosts(): List<Post> {
        return try {
            withContext(Dispatchers.IO){
                client.get("https://jsonplaceholder.typicode.com/posts"){
                    contentType(ContentType.Application.Json)
                }.body<List<Post>>()
            }
        } catch (e: Exception){
            logMessage("Fehler beim Laden der Posts: ${e.message}")
            emptyList()
        }
    }

    suspend fun getPostsByUserId(userId: Int? = null): List<Post> {
        return try {
            withContext(Dispatchers.IO){
                client.get("https://jsonplaceholder.typicode.com/posts?userId=${userId}"){
                    contentType(ContentType.Application.Json)
                }.body<List<Post>>()
            }
        } catch (e: Exception){
            logMessage("Fehler beim Laden der Posts nach userId: ${e.message}")
            emptyList()
        }
    }


    suspend fun getPostById(id: Int?): Post? {
        return try {
            withContext(Dispatchers.IO){
                client.get("https://jsonplaceholder.typicode.com/posts/${id}"){
                    contentType(ContentType.Application.Json)
                }.body<Post>()
            }
        } catch (e: Exception){
            logMessage("Fehler beim Laden des Posts nach Id: ${e.message}")
            null
        }
    }

    suspend fun createPost(newPost: Post): Post?{
        return try {
            withContext(Dispatchers.IO){
                client.post("https://jsonplaceholder.typicode.com/posts"){
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    setBody(newPost)
                }.body<Post>()
            }
        } catch (e: Exception){
            logMessage("Fehler beim Erstellen des Posts: ${e.message}")
            null
        }
    }

    suspend fun updatePost(post: Post): Post?{
        return try {
            withContext(Dispatchers.IO){
                client.put("https://jsonplaceholder.typicode.com/posts/${post.id}"){
                    contentType(ContentType.Application.Json)
                    setBody(post)
                }.body<Post>()
            }
        } catch (e: Exception){
            logMessage("Fehler beim Aktualisieren des Posts: ${e.message}")
            null
        }
    }
}


fun postsDataFlow(): Flow<List<PostRepository.Post>> = flow {
    emit(getPosts())
}

fun getPostsByIdFlow(userId: Int?): Flow<List<PostRepository.Post>> = flow {
    emit(getPostsByUserId(userId))
}

fun getPostByIdFlow1(id: Int?): Flow<PostRepository.Post?> = flow {
    emit(getPostById(id))
}


interface PostsRepository{
    fun getPostsFlow(): Flow<List<PostRepository.Post>>
}

interface GetPostsByUserId{
    fun getPostsByUserIdFlow(userId: Int?): Flow<List<PostRepository.Post>>
}

interface GetPostById{
    fun getPostByIdFlow(id: Int?): Flow<PostRepository.Post?>
}

interface CreatePost{
    suspend fun createPost(newPost: PostRepository.Post): PostRepository.Post?
}

interface UpdatePost{
    suspend fun updatePost(post: PostRepository.Post): PostRepository.Post?
}

class PostsRepositoryImplFlow: PostsRepository{
    override fun getPostsFlow(): Flow<List<PostRepository.Post>> = postsDataFlow()


}

class GetPostsByUserIdImplFlow: GetPostsByUserId{
    override fun getPostsByUserIdFlow(userId: Int?): Flow<List<PostRepository.Post>> = getPostsByIdFlow(userId)


}

class GetPostByIdImplFlow: GetPostById{
    override fun getPostByIdFlow(id: Int?): Flow<PostRepository.Post?> = getPostByIdFlow1(id)
}

class CreatePostImpl: CreatePost{
    override suspend fun createPost(newPost: PostRepository.Post): PostRepository.Post? {
        return PostRepository.createPost(newPost)
    }
}

class UpdatePostImpl: UpdatePost{
    override suspend fun updatePost(post: PostRepository.Post): PostRepository.Post? {
        return PostRepository.updatePost(post)
    }
}