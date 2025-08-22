package org.example.multiplatformhttprequests.usecases

import kotlinx.coroutines.flow.Flow
import org.example.multiplatformhttprequests.data.GetPostsByUserId
import org.example.multiplatformhttprequests.data.PostRepository

class GetPostsByUserIdUseCase(private val posts: GetPostsByUserId) {
    fun getPostsByUserIdFlow(userId: Int?): Flow<List<PostRepository.Post>> {
        return posts.getPostsByUserIdFlow(userId)
    }
}