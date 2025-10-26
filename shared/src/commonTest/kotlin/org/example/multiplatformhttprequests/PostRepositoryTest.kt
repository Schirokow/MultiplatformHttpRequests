package org.example.multiplatformhttprequests

//import io.ktor.client.HttpClient
//import kotlinx.coroutines.test.runTest
//import org.example.multiplatformhttprequests.data.PostRepository
//import kotlin.test.Test
//import kotlin.test.assertEquals
//import kotlin.test.assertNull
//
//class PostRepositoryTest {
//
//    private val fakeHttpClient = FakeHttpClient()
//    private val repository = PostRepository
//
//    @Test
//    fun `getPosts returns posts successfully`() = runTest {
//        // Arrange
//        val testPosts = listOf(
//            PostRepository.Post(userId = 1, id = 1, title = "Test Post 1", body = "Body 1"),
//            PostRepository.Post(userId = 2, id = 2, title = "Test Post 2", body = "Body 2")
//        )
//        fakeHttpClient.givenPosts("https://jsonplaceholder.typicode.com/posts", testPosts)
//
//        // Act
//        val result = repository.getPosts()
//
//        // Assert
//        assertEquals(testPosts, result)
//    }
//
//    @Test
//    fun `getPosts returns empty list on error`() = runTest {
//        // Arrange
//        fakeHttpClient.givenException(Exception("Network error"))
//
//        // Act
//        val result = repository.getPosts()
//
//        // Assert
//        assertEquals(emptyList(), result)
//    }
//
//    @Test
//    fun `getPostsByUserId returns posts for given userId`() = runTest {
//        // Arrange
//        val userId = 1
//        val testPosts = listOf(
//            PostRepository.Post(userId = userId, id = 1, title = "Test Post 1", body = "Body 1")
//        )
//        fakeHttpClient.givenPosts("https://jsonplaceholder.typicode.com/posts?userId=$userId", testPosts)
//
//        // Act
//        val result = repository.getPostsByUserId(userId)
//
//        // Assert
//        assertEquals(testPosts, result)
//    }
//
//    @Test
//    fun `getPostsByUserId returns empty list on error`() = runTest {
//        // Arrange
//        fakeHttpClient.givenException(Exception("Network error"))
//
//        // Act
//        val result = repository.getPostsByUserId(1)
//
//        // Assert
//        assertEquals(emptyList(), result)
//    }
//
//    @Test
//    fun `getPostById returns post successfully`() = runTest {
//        // Arrange
//        val testPost = PostRepository.Post(userId = 1, id = 1, title = "Test Post", body = "Body")
//        fakeHttpClient.givenSinglePost(testPost)
//
//        // Act
//        val result = repository.getPostById(1)
//
//        // Assert
//        assertEquals(testPost, result)
//    }
//
//    @Test
//    fun `getPostById returns null on error`() = runTest {
//        // Arrange
//        fakeHttpClient.givenException(Exception("Network error"))
//
//        // Act
//        val result = repository.getPostById(1)
//
//        // Assert
//        assertNull(result)
//    }
//
//    @Test
//    fun `createPost returns created post successfully`() = runTest {
//        // Arrange
//        val newPost = PostRepository.Post(userId = 1, title = "New Post", body = "New Body")
//        val createdPost = PostRepository.Post(userId = 1, id = 101, title = "New Post", body = "New Body")
//        fakeHttpClient.givenPostResponse(createdPost)
//
//        // Act
//        val result = repository.createPost(newPost)
//
//        // Assert
//        assertEquals(createdPost, result)
//    }
//
//    @Test
//    fun `createPost returns null on error`() = runTest {
//        // Arrange
//        val newPost = PostRepository.Post(userId = 1, title = "New Post", body = "New Body")
//        fakeHttpClient.givenException(Exception("Network error"))
//
//        // Act
//        val result = repository.createPost(newPost)
//
//        // Assert
//        assertNull(result)
//    }
//
//    @Test
//    fun `updatePost returns updated post successfully`() = runTest {
//        // Arrange
//        val updatedPost = PostRepository.Post(userId = 1, id = 1, title = "Updated Post", body = "Updated Body")
//        fakeHttpClient.givenPostResponse(updatedPost)
//
//        // Act
//        val result = repository.updatePost(updatedPost)
//
//        // Assert
//        assertEquals(updatedPost, result)
//    }
//
//    @Test
//    fun `updatePost returns null on error`() = runTest {
//        // Arrange
//        val updatedPost = PostRepository.Post(userId = 1, id = 1, title = "Updated Post", body = "Updated Body")
//        fakeHttpClient.givenException(Exception("Network error"))
//
//        // Act
//        val result = repository.updatePost(updatedPost)
//
//        // Assert
//        assertNull(result)
//    }
//}