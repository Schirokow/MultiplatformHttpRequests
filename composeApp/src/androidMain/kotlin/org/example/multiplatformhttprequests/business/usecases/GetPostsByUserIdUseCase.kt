package com.example.bunghttprequests.business.usecases

import org.example.multiplatformhttprequests.data.GetPostsByUserId
import org.example.multiplatformhttprequests.data.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsByUserIdUseCase(private val posts: GetPostsByUserId) {
    fun getPostsByUserIdFlow(userId: Int?): Flow<List<PostRepository.Post>> {
        return posts.getPostsByUserIdFlow(userId)
    }
}