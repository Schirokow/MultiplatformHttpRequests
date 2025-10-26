package org.example.multiplatformhttprequests

//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runTest
//import org.example.multiplatformhttprequests.data.LocalStorageService
//import org.example.multiplatformhttprequests.data.PostRepository
//import org.example.multiplatformhttprequests.usecases.CreatePostUseCase
//import org.example.multiplatformhttprequests.usecases.GetPostByIdUseCase
//import org.example.multiplatformhttprequests.usecases.GetPostsByUserIdUseCase
//import org.example.multiplatformhttprequests.usecases.GetPostsUseCase
//import org.example.multiplatformhttprequests.usecases.UpdatePostUseCase
//import org.example.multiplatformhttprequests.viewmodels.PostsViewModel
//import kotlin.test.Test
//import kotlin.test.assertEquals
//import kotlin.test.assertFalse
//import kotlin.test.assertTrue
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
//    fun `loadAllPosts updates localStorageState`() = runTest {
//        // Arrange
//        val testPosts = listOf(PostRepository.Post(userId = 1, id = 1, title = "Test", body = "Body"))
//        val localPosts = testPosts.map { LocalStorageService.LocalPostStorage(it.userId, it.id ?: 0, it.title, it.body) }
//        coEvery { getPostsUseCase.getPostsFlow() } returns flowOf(testPosts)
//        coEvery { localPostStorage.insertLocalPosts(testPosts) } returns Unit
//        coEvery { localPostStorage.getAllLocalPosts() } returns flowOf(localPosts)
//
//        // Act
//        viewModel.loadAllPosts()
//
//        // Assert
//        val localStorageState = viewModel.localStorageState.value
//        assertEquals(localPosts, localStorageState)
//        assertFalse(viewModel.isLoading.value)
//    }
//
//    @Test
//    fun `loadPostsByUserId updates localStorageState`() = runTest {
//        // Arrange
//        val userId = 1
//        val testPosts = listOf(PostRepository.Post(userId = userId, id = 1, title = "Test", body = "Body"))
//        val localPosts = testPosts.map { LocalStorageService.LocalPostStorage(it.userId, it.id ?: 0, it.title, it.body) }
//        coEvery { getPostsByUserIdUseCase.getPostsByUserIdFlow(userId) } returns flowOf(testPosts)
//        coEvery { localPostStorage.insertLocalPosts(testPosts) } returns Unit
//        coEvery { localPostStorage.getAllLocalPosts() } returns flowOf(localPosts)
//
//        // Act
//        viewModel.loadPostsByUserId(userId)
//
//        // Assert
//        val localStorageState = viewModel.localStorageState.value
//        assertEquals(localPosts, localStorageState)
//        assertFalse(viewModel.isLoading.value)
//    }
//
//    @Test
//    fun `loadPostById inserts post to local storage`() = runTest {
//        // Arrange
//        val postId = 1
//        val testPost = PostRepository.Post(userId = 1, id = postId, title = "Test", body = "Body")
//        val localPost = LocalStorageService.LocalPostStorage(userId = 1, id = postId, title = "Test", body = "Body")
//        coEvery { getPostByIdUseCase.getPostByIdFlow(postId) } returns flowOf(testPost)
//        coEvery { localPostStorage.insertNewPost(localPost) } returns Unit
//
//        // Act
//        viewModel.loadPostById(postId)
//
//        // Assert
//        assertFalse(viewModel.isLoading.value)
//    }
//
//    @Test
//    fun `createNewPost calls createPostUseCase`() = runTest {
//        // Arrange
//        val newPost = PostRepository.Post(userId = 1, title = "New Post", body = "New Body")
//        coEvery { createPostUseCase.createNewPost(newPost) } returns newPost
//        coEvery { localPostStorage.insertNewPost(any()) } returns Unit
//
//        // Act
//        viewModel.createNewPost(newPost)
//
//        // Assert
//        assertFalse(viewModel.isLoading.value)
//    }
//
//    @Test
//    fun `updatePost calls updatePostUseCase`() = runTest {
//        // Arrange
//        val updatedPost = PostRepository.Post(userId = 1, id = 1, title = "Updated Post", body = "Updated Body")
//        coEvery { updatePostUseCase.updatePost(updatedPost) } returns updatedPost
//        coEvery { localPostStorage.insertNewPost(any()) } returns Unit
//
//        // Act
//        viewModel.updatePost(updatedPost)
//
//        // Assert
//        assertFalse(viewModel.isLoading.value)
//    }
//
//    @Test
//    fun `deleteAllPosts clears local storage`() = runTest {
//        // Arrange
//        coEvery { localPostStorage.deleteAllLocalPosts() } returns Unit
//        coEvery { localPostStorage.getAllLocalPosts() } returns flowOf(emptyList())
//
//        // Act
//        viewModel.deleteAllPosts()
//
//        // Assert
//        assertEquals(emptyList(), viewModel.localStorageState.value)
//        assertFalse(viewModel.isLoading.value)
//    }
//}