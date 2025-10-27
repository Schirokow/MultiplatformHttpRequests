package org.example.multiplatformhttprequests

//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.test.runTest
//import org.example.multiplatformhttprequests.data.CreatePost
//import org.example.multiplatformhttprequests.data.Post
//import org.example.multiplatformhttprequests.usecases.CreatePostUseCase
//import org.koin.core.context.startKoin
//import org.koin.dsl.module
//import org.koin.test.KoinTest
//import kotlin.test.Test
//import kotlin.test.assertEquals
//import kotlin.test.assertNull
//
//class CreatePostUseCaseTest : KoinTest {
//
//    private val createPost = mockk<CreatePost>()
//    private val useCase = CreatePostUseCase()
//
//    init {
//        // Koin-Modul für Tests
//        startKoin {
//            modules(module {
//                single<CreatePost> { createPost }
//            })
//        }
//    }
//
//    @Test
//    fun `createNewPost gibt neuen Post zurück wenn es klappt`() = runTest {
//        // Vorbereiten: Testdaten
//        val newPost = Post(userId = 1, title = "Neuer Post", body = "Neuer Text")
//        val createdPost = Post(userId = 1, id = 101, title = "Neuer Post", body = "Neuer Text")
//        coEvery { createPost.createPost(newPost) } returns createdPost
//
//        // Aktion: Neuen Post erstellen
//        val result = useCase.createNewPost(newPost)
//
//        // Prüfen: Ist der erstellte Post korrekt?
//        assertEquals(createdPost, result)
//    }
//
//    @Test
//    fun `createNewPost gibt null zurück wenn es fehlschlägt`() = runTest {
//        // Vorbereiten: Simuliere einen Fehler
//        val newPost = Post(userId = 1, title = "Neuer Post", body = "Neuer Text")
//        coEvery { createPost.createPost(newPost) } returns null
//
//        // Aktion: Neuen Post erstellen
//        val result = useCase.createNewPost(newPost)
//
//        // Prüfen: Ist das Ergebnis null?
//        assertNull(result)
//    }
//}