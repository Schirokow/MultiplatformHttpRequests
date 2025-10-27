package org.example.multiplatformhttprequests


import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.example.multiplatformhttprequests.data.Post
import org.example.multiplatformhttprequests.data.PostsRepository
import org.example.multiplatformhttprequests.usecases.GetPostsUseCase
import kotlin.test.*

class GetPostsUseCaseTest {

    private class FakeRepo : PostsRepository {
        override fun getPostsFlow() = flowOf(listOf(Post(1, 1, "Title", "Body")))
    }

    @Test
    fun testUseCaseEmitsPosts() = runTest {
        val useCase = GetPostsUseCase(FakeRepo())
        val result = useCase.getPosts().first()

        assertEquals(1, result.size)
        assertEquals("Title", result.first().title)
    }
}









//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.test.runTest
//import org.example.multiplatformhttprequests.data.Post
//import org.example.multiplatformhttprequests.data.PostsRepository
//import org.example.multiplatformhttprequests.usecases.GetPostsUseCase
//import kotlin.test.Test
//import kotlin.test.assertEquals
//
//class GetPostsUseCaseTest {
//
//    private val postsRepository = mockk<PostsRepository>()
//    private val useCase = GetPostsUseCase(postsRepository)
//
//    @Test
//    fun `getPosts gibt Posts zurück`() = runTest {
//        // Vorbereiten: Testdaten
//        val testPosts = listOf(
//            Post(userId = 1, id = 1, title = "Post 1", body = "Text 1")
//        )
//        coEvery { postsRepository.getPostsFlow() } returns flowOf(testPosts)
//
//        // Aktion: Posts abrufen
//        val result = useCase.getPosts().first()
//
//        // Prüfen: Sind die Posts korrekt?
//        assertEquals(testPosts, result)
//    }
//
//    @Test
//    fun `getPosts gibt leere Liste zurück wenn Repository leer ist`() = runTest {
//        // Vorbereiten: Simuliere leere Liste
//        coEvery { postsRepository.getPostsFlow() } returns flowOf(emptyList())
//
//        // Aktion: Posts abrufen
//        val result = useCase.getPosts().first()
//
//        // Prüfen: Ist die Liste leer?
//        assertEquals(emptyList(), result)
//    }
//}