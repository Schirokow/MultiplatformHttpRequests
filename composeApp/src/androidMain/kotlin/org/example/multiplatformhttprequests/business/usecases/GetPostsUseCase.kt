package com.example.bunghttprequests.business.usecases

import org.example.multiplatformhttprequests.data.PostRepository
import org.example.multiplatformhttprequests.data.PostsRepository
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(private val posts: PostsRepository) {
//    private val posts = PostsRepositoryImplFlow()
    fun getPostsFlow(): Flow<List<PostRepository.Post>> {
        return posts.getPostsFlow()
    }
}

