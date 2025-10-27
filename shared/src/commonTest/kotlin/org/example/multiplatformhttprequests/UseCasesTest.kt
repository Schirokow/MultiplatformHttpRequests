package org.example.multiplatformhttprequests

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.example.multiplatformhttprequests.data.*
import org.example.multiplatformhttprequests.usecases.*
import kotlin.test.*

class UseCasesTest {

    // --- 1 GetPostsUseCase ---
    @Test
    fun testGetPostsUseCaseReturnsFlow() = runTest {
        val fakeRepo = object : PostsRepository {
            override fun getPostsFlow() = flowOf(listOf(Post(1, 1, "Test Title", "Body")))
        }

        val useCase = GetPostsUseCase(fakeRepo)
        val result = useCase.getPosts().first()

        assertEquals(1, result.size)
        assertEquals("Test Title", result.first().title)
    }

    // --- 2️ CreatePostUseCase ---
    @Test
    fun testCreatePostUseCaseCreatesPost() = runTest {
        val fakeCreatePost = object : CreatePost {
            override suspend fun createPost(newPost: Post): Post? {
                return newPost
            }
        }

        val useCase = CreatePostUseCase(fakeCreatePost)
        val input = Post(1, 1, "Created Post", "Body")

        val result = useCase.createNewPost(input)

        assertEquals(input, result)
        assertEquals("Created Post", result?.title ?: "")
    }


    // --- 3️ UpdatePostUseCase ---
    @Test
    fun testUpdatePostUseCaseUpdatesPost() = runTest {
        val fakeUpdater = object : UpdatePost {
            override suspend fun updatePost(post: Post) = post.copy(title = "Updated Title")
        }

        val useCase = UpdatePostUseCase(fakeUpdater)
        val result = useCase.updatePost(Post(1, 1, "Old Title", "Body"))

        assertEquals("Updated Title", result?.title ?: "")
    }

    // --- 4️ GetPostsByUserIdUseCase ---
    @Test
    fun testGetPostsByUserIdUseCaseReturnsCorrectUserPosts() = runTest {
        val fakeData = listOf(
            Post(1, 1, "User1 Post", "Body"),
            Post(2, 2, "User2 Post", "Body")
        )

        val fakeGetter = object : GetPostsByUserId {
            override fun getPostsByUserIdFlow(userId: Int?) =
                flowOf(fakeData.filter { it.userId == userId })
        }

        val useCase = GetPostsByUserIdUseCase(fakeGetter)
        val result = useCase.getPostsByUserId(1).first()

        assertEquals(1, result.size)
        assertEquals("User1 Post", result.first().title)
    }

    // --- 5️ GetPostByIdUseCase ---
    @Test
    fun testGetPostByIdUseCaseReturnsCorrectPost() = runTest {
//        val fakeData = listOf(
//            Post(1, 1, "First Post", "Body"),
//            Post(2, 1, "Second Post", "Body")
//        )

        val fakeData = listOf(
            Post(userId = 1, id = 1, title = "First Post", body = "Body"),
            Post(userId = 1, id = 2, title = "Second Post", body = "Body")
        )

        val fakeRepository = object : GetPostById {
            override fun getPostByIdFlow(id: Int?) = flowOf(
                fakeData.find { post ->
                    println("Checking post.id=${post.id} against id=$id")
                    post.id == id
                }
            )
        }

        val useCase = GetPostByIdUseCase(fakeRepository)
        val result = useCase.getPostById(2).first()

        println("Received post: $result")

        assertNotNull(result, "Expected non-null Post for ID 2")
        assertEquals("Second Post", result.title)
    }
}