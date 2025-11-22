package org.example.multiplatformhttprequests

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.example.multiplatformhttprequests.data.GetPostsByUserId
import org.example.multiplatformhttprequests.data.Post
import org.example.multiplatformhttprequests.data.PostApiService
import org.example.multiplatformhttprequests.data.PostsRepository
import org.example.multiplatformhttprequests.usecases.GetPostsByUserIdUseCase
import org.example.multiplatformhttprequests.usecases.GetPostsUseCase
import kotlin.test.Test

open class FakePostApiService() {

    val testPostData = listOf<Post>(
        Post(userId = 1, id = 1, title = "Test1", body = "Post1"),
        Post(userId = 2, id = 2, title = "Test2", body = "Post2 and id 2"),
        Post(userId = 3, id = 1, title = "Test3", body = "Post1 and userId 2")
    )

    val fakePostById: Post = Post(userId = 1, id = 1, title = "Test", body = "TestPostById")


    suspend fun getPosts(): List<Post> {
        return try {
            withContext(Dispatchers.IO){
                testPostData
            }
        } catch (e: Exception){
            logMessage("Fehler beim Laden der Posts: ${e.message}")
            emptyList()
        }
    }

    suspend fun getPostsByUserId(userId: Int? = null): List<Post> {

        return try {
            withContext(Dispatchers.IO) {
                if (userId != null) {
                    testPostData.filter { it.userId == userId }  // Filtert nach userId
                } else {
                    emptyList()
                }
            }
        } catch (e: Exception) {
            logMessage("Fehler beim Laden der Posts nach userId: ${e.message}")
            emptyList()
        }
    }


//    suspend fun getPostById(id: Int?): Post? {
//        return try {
//            withContext(Dispatchers.IO) {
//                if (id != null) {
//                    fakePostById.filter { it.id == id }  // Filtert nach userId
//                } else {
//                    fakePostById // Falls userId null ist, werden alle Posts zurückgegeben
//                }
//            }
//        } catch (e: Exception) {
//            logMessage("Fehler beim Laden des Posts nach Id: ${e.message}")
//            null
//        }
//    }
//
//    suspend fun createPost(newPost: Post): Post? {
//        return try {
//            withContext(Dispatchers.IO) {
//                client.post("https://jsonplaceholder.typicode.com/posts") {
//                    method = HttpMethod.Post
//                    contentType(ContentType.Application.Json)
//                    setBody(newPost)
//                }.body<Post>()
//            }
//        } catch (e: Exception) {
//            logMessage("Fehler beim Erstellen des Posts: ${e.message}")
//            null
//        }
//    }
//
//    suspend fun updatePost(post: Post): Post? {
//        return try {
//            withContext(Dispatchers.IO) {
//                client.put("https://jsonplaceholder.typicode.com/posts/${post.id}") {
//                    contentType(ContentType.Application.Json)
//                    setBody(post)
//                }.body<Post>()
//            }
//        } catch (e: Exception) {
//            logMessage("Fehler beim Aktualisieren des Posts: ${e.message}")
//            null
//        }
//    }
}


class FakePostsRepositoryImpl(private val api: FakePostApiService): PostsRepository{
    override fun getPostsFlow(): Flow<List<Post>> {
        return flow {
            emit(api.getPosts())
        }
    }
}

class FakeGetPostsByUserIdImpl(private val api: FakePostApiService): GetPostsByUserId{
    override fun getPostsByUserIdFlow(userId: Int?): Flow<List<Post>> {
        return flow {
            emit(api.getPostsByUserId(userId))
        }
    }
}



class FakeUseCaseTest {

    // Wir erstellen die Instanz, die wir testen wollen
    private val useCase = GetPostsUseCase(FakePostsRepositoryImpl(FakePostApiService()))
    private val getPostsByUserIdUseCase = GetPostsByUserIdUseCase(FakeGetPostsByUserIdImpl(FakePostApiService()))

    @Test
    fun `should return false if not List of Posts`() {
        // When
        val postData = useCase.getPosts()

        // Then
        assertThat(postData).equals(FakePostApiService().testPostData)
    }

    @Test
    fun `should return false if postData wrong`() = runTest {

        // When
        val flow = getPostsByUserIdUseCase.getPostsByUserId(1)
        val result = mutableListOf<List<Post>>()

        // Flow sammeln
        flow.collect { result.add(it) }

        // Then - Korrekter Vergleich
        val expectedPosts = listOf(
            Post(userId = 1, id = 1, title = "Test1", body = "Post1")
        )

        // Prüfe ob die erste Emission die erwarteten Posts enthält
        assertThat(result[0]).isEqualTo(expectedPosts)



    }
//
//    @Test
//    fun `should return true for passwords that meet all criteria`() {
//        // When
//        val isStrong = useCase.execute("strongPassword123")
//
//        // Then
//        assertThat(isStrong).isTrue()
//    }
}
