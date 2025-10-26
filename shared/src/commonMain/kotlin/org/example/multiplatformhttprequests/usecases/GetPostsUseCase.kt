package org.example.multiplatformhttprequests.usecases

import kotlinx.coroutines.flow.Flow
import org.example.multiplatformhttprequests.data.Post
import org.example.multiplatformhttprequests.data.PostsRepository

class GetPostsUseCase(private val posts: PostsRepository) {
//    private val posts = PostsRepositoryImplFlow()
    fun getPosts(): Flow<List<Post>> {
        return posts.getPostsFlow()
    }
}