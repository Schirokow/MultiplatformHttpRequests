package org.example.multiplatformhttprequests


import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.example.multiplatformhttprequests.data.CreatePost
import org.example.multiplatformhttprequests.data.GetPostById
import org.example.multiplatformhttprequests.data.GetPostsByUserId
import org.example.multiplatformhttprequests.data.LocalStorageService
import org.example.multiplatformhttprequests.data.Post
import org.example.multiplatformhttprequests.data.UpdatePost
import org.example.multiplatformhttprequests.usecases.*
import org.example.multiplatformhttprequests.viewmodels.PostsViewModel
import kotlin.test.*

class PostsViewModelTest {

    private class FakeLocalStorage : LocalStorageService.LocalPostsStorage {
        val insertedPosts = mutableListOf<Post>()
        override suspend fun insertLocalPosts(post: List<Post>) {
            insertedPosts.addAll(post)
        }
        override suspend fun insertNewPost(post: LocalStorageService.LocalPostStorage) {}
        override fun getAllLocalPosts() = flowOf(emptyList<LocalStorageService.LocalPostStorage>())
        override suspend fun getLocalPostById(id: Int) = null
        override suspend fun deleteLocalPostById(id: Int) {}
        override suspend fun deleteAllLocalPosts() {}
    }

    private class FakeGetPostsUseCase : GetPostsUseCase(
        posts = object : org.example.multiplatformhttprequests.data.PostsRepository {
            override fun getPostsFlow() = flowOf(listOf(Post(1, 1, "VM Test", "Body")))
        }
    )

    @Test
    fun testLoadAllPostsInsertsIntoLocalStorage() = runTest {
        val fakeStorage = FakeLocalStorage()
        val vm = PostsViewModel(
            localPostStorage = fakeStorage,
            getPostsUseCase = FakeGetPostsUseCase(),
            createPostUseCase = CreatePostUseCase(
                createPost = object : CreatePost {
                    override suspend fun createPost(newPost: Post): Post? {
                        return newPost.copy(title = "Created by Fake")
                    }
                }
            ),
            updatePostUseCase = UpdatePostUseCase(object : UpdatePost {
                override suspend fun updatePost(post: Post) = post
            }),
            getPostsByUserIdUseCase = GetPostsByUserIdUseCase(object : GetPostsByUserId {
                override fun getPostsByUserIdFlow(userId: Int?) = flowOf(emptyList<Post>())
            }),
            getPostByIdUseCase = GetPostByIdUseCase(object : GetPostById {
                override fun getPostByIdFlow(id: Int?) = flowOf(null)
            })
        )

        // Aktion starten
        vm.loadAllPosts()

        // ⏳ Sicherstellen, dass Coroutine fertig läuft
        advanceUntilIdle()  // aus kotlinx-coroutines-test

        // Jetzt prüfen
        assertTrue(fakeStorage.insertedPosts.isNotEmpty(), "Storage should contain inserted posts")
        assertEquals("VM Test", fakeStorage.insertedPosts.first().title)
    }
}









//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runTest
//import org.example.multiplatformhttprequests.data.LocalStorageService
//import org.example.multiplatformhttprequests.data.Post
//import org.example.multiplatformhttprequests.usecases.CreatePostUseCase
//import org.example.multiplatformhttprequests.usecases.GetPostByIdUseCase
//import org.example.multiplatformhttprequests.usecases.GetPostsByUserIdUseCase
//import org.example.multiplatformhttprequests.usecases.GetPostsUseCase
//import org.example.multiplatformhttprequests.usecases.UpdatePostUseCase
//import org.example.multiplatformhttprequests.viewmodels.PostsViewModel
//import kotlin.test.Test
//import kotlin.test.assertEquals
//import kotlin.test.assertFalse
//
//class PostsViewModelTest {
//
//    private val localPostStorage = mockk<LocalStorageService.LocalPostsStorage>()
//    private val getPostsUseCase = mockk<GetPostsUseCase>()
//    private val createPostUseCase = mockk<CreatePostUseCase>()
//    private val updatePostUseCase = mockk<UpdatePostUseCase>()
//    private val getPostsByUserIdUseCase = mockk<GetPostsByUserIdUseCase>()
//    private val getPostByIdUseCase = mockk<GetPostByIdUseCase>()
//
//    private val viewModel = PostsViewModel(
//        localPostStorage,
//        getPostsUseCase,
//        createPostUseCase,
//        updatePostUseCase,
//        getPostsByUserIdUseCase,
//        getPostByIdUseCase
//    )
//
//    @Test
//    fun `loadAllPosts speichert Posts in localStorage`() = runTest {
//        // Vorbereiten: Testdaten
//        val testPosts = listOf(
//            Post(userId = 1, id = 1, title = "Post 1", body = "Text 1")
//        )
//        val localPosts = testPosts.map {
//            LocalStorageService.LocalPostStorage(id = it.id ?: 0, userId = it.userId, title = it.title, body = it.body)
//        }
//        coEvery { getPostsUseCase.getPosts() } returns flowOf(testPosts)
//        coEvery { localPostStorage.insertLocalPosts(testPosts) } returns Unit
//        coEvery { localPostStorage.getAllLocalPosts() } returns flowOf(localPosts)
//
//        // Aktion: Alle Posts laden
//        viewModel.loadAllPosts()
//
//        // Prüfen: Wurden die Posts in localStorageState gespeichert?
//        assertEquals(localPosts, viewModel.localStorageState.value)
//        assertFalse(viewModel.isLoading.value)
//    }
//
//    @Test
//    fun `createNewPost ruft createPostUseCase auf`() = runTest {
//        // Vorbereiten: Testdaten
//        val newPost = Post(userId = 1, title = "Neuer Post", body = "Neuer Text")
//        coEvery { createPostUseCase.createNewPost(newPost) } returns newPost
//        coEvery { localPostStorage.insertNewPost(any()) } returns Unit
//
//        // Aktion: Neuen Post erstellen
//        viewModel.createNewPost(newPost)
//
//        // Prüfen: Ist isLoading false?
//        assertFalse(viewModel.isLoading.value)
//    }
//
//    @Test
//    fun `deleteAllPosts löscht alle Posts`() = runTest {
//        // Vorbereiten: Simuliere leere lokale Speicherung
//        coEvery { localPostStorage.deleteAllLocalPosts() } returns Unit
//        coEvery { localPostStorage.getAllLocalPosts() } returns flowOf(emptyList())
//
//        // Aktion: Alle Posts löschen
//        viewModel.deleteAllPosts()
//
//        // Prüfen: Ist localStorageState leer?
//        assertEquals(emptyList(), viewModel.localStorageState.value)
//        assertFalse(viewModel.isLoading.value)
//    }
//}