package org.example.multiplatformhttprequests

//import org.example.multiplatformhttprequests.data.CreatePostImpl
//import org.example.multiplatformhttprequests.data.Post
//import org.example.multiplatformhttprequests.data.PostApiService
//import kotlin.test.Test
//
//class CreatePostImplTest {
//
//    private val apiService = mockk<PostApiService>()
//    private val createPost = CreatePostImpl(apiService)
//
//    @Test
//    fun `createPost gibt neuen Post zurück wenn es klappt`() = runTest {
//        // Vorbereiten: Testdaten
//        val newPost = Post(userId = 1, title = "Neuer Post", body = "Neuer Text")
//        val createdPost = Post(userId = 1, id = 101, title = "Neuer Post", body = "Neuer Text")
//        coEvery { apiService.createPost(newPost) } returns createdPost
//
//        // Aktion: Neuen Post erstellen
//        val result = createPost.createPost(newPost)
//
//        // Prüfen: Ist der erstellte Post korrekt?
//        assertEquals(createdPost, result)
//    }
//
//    @Test
//    fun `createPost gibt null zurück wenn es fehlschlägt`() = runTest {
//        // Vorbereiten: Simuliere einen Fehler
//        val newPost = Post(userId = 1, title = "Neuer Post", body = "Neuer Text")
//        coEvery { apiService.createPost(newPost) } returns null
//
//        // Aktion: Neuen Post erstellen
//        val result = createPost.createPost(newPost)
//
//        // Prüfen: Ist das Ergebnis null?
//        assertNull(result)
//    }
//}