package org.example.multiplatformhttprequests


import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.example.multiplatformhttprequests.data.Post
import org.example.multiplatformhttprequests.data.PostApiService
import org.example.multiplatformhttprequests.data.PostsRepositoryImpl
import kotlin.test.*

class PostsRepositoryImplTest {

    private class FakeApi : PostApiService(HttpClient()) {
        override suspend fun getPosts(): List<Post> {
            return listOf(Post(1, 10, "Repo Test", "Works fine"))
        }
    }

    @Test
    fun testGetPostsFlowEmitsData() = runTest {
        val repo = PostsRepositoryImpl(FakeApi())
        val result = repo.getPostsFlow().first()

        assertEquals(1, result.size)
        assertEquals("Repo Test", result.first().title)
    }
}







//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.test.runTest
//import org.example.multiplatformhttprequests.data.Post
//import org.example.multiplatformhttprequests.data.PostApiService
//import org.example.multiplatformhttprequests.data.PostsRepositoryImpl
//import kotlin.test.Test
//import kotlin.test.assertEquals
//
//class PostsRepositoryImplTest {
//
//    private val apiService = mockk<PostApiService>()
//    private val repository = PostsRepositoryImpl(apiService)
//
//    @Test
//    fun `getPostsFlow gibt Posts zurück`() = runTest {
//        // Vorbereiten: Testdaten
//        val testPosts = listOf(
//            Post(userId = 1, id = 1, title = "Post 1", body = "Text 1")
//        )
//        coEvery { apiService.getPosts() } returns testPosts
//
//        // Aktion: Posts als Flow abrufen
//        val result = repository.getPostsFlow().first()
//
//        // Prüfen: Sind die Posts korrekt?
//        assertEquals(testPosts, result)
//    }
//
//    @Test
//    fun `getPostsFlow gibt leere Liste zurück wenn API fehlschlägt`() = runTest {
//        // Vorbereiten: Simuliere leere Liste
//        coEvery { apiService.getPosts() } returns emptyList()
//
//        // Aktion: Posts als Flow abrufen
//        val result = repository.getPostsFlow().first()
//
//        // Prüfen: Ist die Liste leer?
//        assertEquals(emptyList(), result)
//    }
//}