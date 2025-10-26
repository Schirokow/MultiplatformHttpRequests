package org.example.multiplatformhttprequests

//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runTest
//import org.example.multiplatformhttprequests.data.GetPostById
//import org.example.multiplatformhttprequests.data.GetPostsByUserId
//import org.example.multiplatformhttprequests.data.PostRepository
//import org.example.multiplatformhttprequests.data.PostsRepository
//import org.example.multiplatformhttprequests.usecases.CreatePostUseCase
//import org.example.multiplatformhttprequests.usecases.GetPostByIdUseCase
//import org.example.multiplatformhttprequests.usecases.GetPostsByUserIdUseCase
//import org.example.multiplatformhttprequests.usecases.GetPostsUseCase
//import org.example.multiplatformhttprequests.usecases.UpdatePostUseCase
//import kotlin.test.Test
//import kotlin.test.assertEquals
//
//class UseCaseTests {
//
//    private val postsRepository = mockk<PostsRepository>()
//    private val getPostsByUserId = mockk<GetPostsByUserId>()
//    private val getPostById = mockk<GetPostById>()
//    private val createPost = mockk<org.example.multiplatformhttprequests.data.CreatePost>()
//    private val updatePost = mockk<org.example.multiplatformhttprequests.data.UpdatePost>()
//
//    @Test
//    fun `GetPostsUseCase returns posts flow`() = runTest {
//        // Arrange
//        val testPosts = listOf(PostRepository.Post(userId = 1, id = 1, title = "Test", body = "Body"))
//        coEvery { postsRepository.getPostsFlow() } returns flowOf(testPosts)
//        val useCase = GetPostsUseCase(postsRepository)
//
//        // Act
//        val result = useCase.getPostsFlow()
//
//        // Assert
//        result.collect { posts ->
//            assertEquals(testPosts, posts)
//        }
//    }
//
//    @Test
//    fun `GetPostsByUserIdUseCase returns posts flow for userId`() = runTest {
//        // Arrange
//        val userId = 1
//        val testPosts = listOf(PostRepository.Post(userId = userId, id = 1, title = "Test", body = "Body"))
//        coEvery { getPostsByUserId.getPostsByUserIdFlow(userId) } returns flowOf(testPosts)
//        val useCase = GetPostsByUserIdUseCase(getPostsByUserId)
//
//        // Act
//        val result = useCase.getPostsByUserIdFlow(userId)
//
//        // Assert
//        result.collect { posts ->
//            assertEquals(testPosts, posts)
//        }
//    }
//
//    @Test
//    fun `GetPostByIdUseCase returns post flow`() = runTest {
//        // Arrange
//        val postId = 1
//        val testPost = PostRepository.Post(userId = 1, id = postId, title = "Test", body = "Body")
//        coEvery { getPostById.getPostByIdFlow(postId) } returns flowOf(testPost)
//        val useCase = GetPostByIdUseCase(getPostById)
//
//        // Act
//        val result = useCase.getPostByIdFlow(postId)
//
//        // Assert
//        result.collect { post ->
//            assertEquals(testPost, post)
//        }
//    }
//
//    @Test
//    fun `CreatePostUseCase creates post successfully`() = runTest {
//        // Arrange
//        val newPost = PostRepository.Post(userId = 1, title = "New Post", body = "New Body")
//        val createdPost = PostRepository.Post(userId = 1, id = 101, title = "New Post", body = "New Body")
//        coEvery { createPost.createPost(newPost) } returns createdPost
//        val useCase = CreatePostUseCase()
//
//        // Act
//        val result = useCase.createNewPost(newPost)
//
//        // Assert
//        assertEquals(createdPost, result)
//    }
//
//    @Test
//    fun `UpdatePostUseCase updates post successfully`() = runTest {
//        // Arrange
//        val updatedPost = PostRepository.Post(userId = 1, id = 1, title = "Updated Post", body = "Updated Body")
//        coEvery { updatePost.updatePost(updatedPost) } returns updatedPost
//        val useCase = UpdatePostUseCase(updatePost)
//
//        // Act
//        val result = useCase.updatePost(updatedPost)
//
//        // Assert
//        assertEquals(updatedPost, result)
//    }
//}