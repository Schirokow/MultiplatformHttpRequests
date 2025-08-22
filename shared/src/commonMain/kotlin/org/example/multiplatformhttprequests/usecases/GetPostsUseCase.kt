package org.example.multiplatformhttprequests.usecases

import kotlinx.coroutines.flow.Flow
import org.example.multiplatformhttprequests.data.PostRepository
import org.example.multiplatformhttprequests.data.PostsRepository

class GetPostsUseCase(private val posts: PostsRepository) {
//    private val posts = PostsRepositoryImplFlow()
    fun getPostsFlow(): Flow<List<PostRepository.Post>> {
        return posts.getPostsFlow()
    }
}