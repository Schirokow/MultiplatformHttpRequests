package org.example.multiplatformhttprequests

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.multiplatformhttprequests.data.Post
import org.example.multiplatformhttprequests.data.PostApiService
import kotlin.test.*

class PostApiServiceTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun testGetPostsReturnsData() = runTest {
        // Given
        val mockResponse = listOf(
            Post(1, 1, "Test title", "Test body"),
            Post(2, 2, "Another title", "Another body")
        )

        val mockEngine = MockEngine { request ->
            respond(
                content = json.encodeToString(mockResponse),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) { json() }
        }

        val api = PostApiService(client)

        // When
        val result = api.getPosts()

        // Then
        assertEquals(2, result.size)
        assertEquals("Test title", result.first().title)
    }

    @Test
    fun testGetPostsHandlesErrorGracefully() = runTest {
        val mockEngine = MockEngine { respondError(HttpStatusCode.InternalServerError) }
        val client = HttpClient(mockEngine) { install(ContentNegotiation) { json() } }
        val api = PostApiService(client)

        val result = api.getPosts()

        assertTrue(result.isEmpty())
    }
}









//import kotlinx.coroutines.test.runTest
//import org.example.multiplatformhttprequests.data.Post
//import org.example.multiplatformhttprequests.data.PostApiService
//import kotlin.test.Test
//import kotlin.test.assertEquals
//import kotlin.test.assertNull
//
//class PostApiServiceTest {
//
//    private val fakeHttpClient = FakeHttpClient()
//    private val apiService = PostApiService(fakeHttpClient)
//
//    @Test
//    fun `getPosts gibt Posts zurück wenn es klappt`() = runTest {
//        // Vorbereiten: Testdaten
//        val testPosts = listOf(
//            Post(userId = 1, id = 1, title = "Post 1", body = "Text 1"),
//            Post(userId = 2, id = 2, title = "Post 2", body = "Text 2")
//        )
//        fakeHttpClient.givenPosts("https://jsonplaceholder.typicode.com/posts", testPosts)
//
//        // Aktion: Posts laden
//        val result = apiService.getPosts()
//
//        // Prüfen: Sind die Posts korrekt?
//        assertEquals(testPosts, result)
//    }
//
//    @Test
//    fun `getPosts gibt leere Liste zurück wenn es fehlschlägt`() = runTest {
//        // Vorbereiten: Simuliere einen Fehler
//        fakeHttpClient.givenException(Exception("Kein Internet"))
//
//        // Aktion: Posts laden
//        val result = apiService.getPosts()
//
//        // Prüfen: Ist die Liste leer?
//        assertEquals(emptyList(), result)
//    }
//
//    @Test
//    fun `getPostsByUserId gibt Posts für userId zurück`() = runTest {
//        // Vorbereiten: Testdaten für userId=1
//        val userId = 1
//        val testPosts = listOf(
//            Post(userId = userId, id = 1, title = "Post 1", body = "Text 1")
//        )
//        fakeHttpClient.givenPosts("https://jsonplaceholder.typicode.com/posts?userId=$userId", testPosts)
//
//        // Aktion: Posts für userId laden
//        val result = apiService.getPostsByUserId(userId)
//
//        // Prüfen: Sind die Posts korrekt?
//        assertEquals(testPosts, result)
//    }
//
//    @Test
//    fun `getPostsByUserId gibt leere Liste zurück wenn es fehlschlägt`() = runTest {
//        // Vorbereiten: Simuliere einen Fehler
//        fakeHttpClient.givenException(Exception("Kein Internet"))
//
//        // Aktion: Posts für userId laden
//        val result = apiService.getPostsByUserId(1)
//
//        // Prüfen: Ist die Liste leer?
//        assertEquals(emptyList(), result)
//    }
//
//    @Test
//    fun `getPostById gibt Post zurück wenn es klappt`() = runTest {
//        // Vorbereiten: Testdaten
//        val testPost = Post(userId = 1, id = 1, title = "Post 1", body = "Text 1")
//        fakeHttpClient.givenSinglePost(testPost)
//
//        // Aktion: Post mit id=1 laden
//        val result = apiService.getPostById(1)
//
//        // Prüfen: Ist der Post korrekt?
//        assertEquals(testPost, result)
//    }
//
//    @Test
//    fun `getPostById gibt null zurück wenn es fehlschlägt`() = runTest {
//        // Vorbereiten: Simuliere einen Fehler
//        fakeHttpClient.givenException(Exception("Kein Internet"))
//
//        // Aktion: Post mit id=1 laden
//        val result = apiService.getPostById(1)
//
//        // Prüfen: Ist das Ergebnis null?
//        assertNull(result)
//    }
//
//    @Test
//    fun `createPost gibt neuen Post zurück wenn es klappt`() = runTest {
//        // Vorbereiten: Testdaten
//        val newPost = Post(userId = 1, title = "Neuer Post", body = "Neuer Text")
//        val createdPost = Post(userId = 1, id = 101, title = "Neuer Post", body = "Neuer Text")
//        fakeHttpClient.givenPostResponse(createdPost)
//
//        // Aktion: Neuen Post erstellen
//        val result = apiService.createPost(newPost)
//
//        // Prüfen: Ist der erstellte Post korrekt?
//        assertEquals(createdPost, result)
//    }
//
//    @Test
//    fun `createPost gibt null zurück wenn es fehlschlägt`() = runTest {
//        // Vorbereiten: Simuliere einen Fehler
//        val newPost = Post(userId = 1, title = "Neuer Post", body = "Neuer Text")
//        fakeHttpClient.givenException(Exception("Kein Internet"))
//
//        // Aktion: Neuen Post erstellen
//        val result = apiService.createPost(newPost)
//
//        // Prüfen: Ist das Ergebnis null?
//        assertNull(result)
//    }
//
//    @Test
//    fun `updatePost gibt aktualisierten Post zurück wenn es klappt`() = runTest {
//        // Vorbereiten: Testdaten
//        val updatedPost = Post(userId = 1, id = 1, title = "Aktualisierter Post", body = "Neuer Text")
//        fakeHttpClient.givenPostResponse(updatedPost)
//
//        // Aktion: Post aktualisieren
//        val result = apiService.updatePost(updatedPost)
//
//        // Prüfen: Ist der aktualisierte Post korrekt?
//        assertEquals(updatedPost, result)
//    }
//
//    @Test
//    fun `updatePost gibt null zurück wenn es fehlschlägt`() = runTest {
//        // Vorbereiten: Simuliere einen Fehler
//        val updatedPost = Post(userId = 1, id = 1, title = "Aktualisierter Post", body = "Neuer Text")
//        fakeHttpClient.givenException(Exception("Kein Internet"))
//
//        // Aktion: Post aktualisieren
//        val result = apiService.updatePost(updatedPost)
//
//        // Prüfen: Ist das Ergebnis null?
//        assertNull(result)
//    }
//}