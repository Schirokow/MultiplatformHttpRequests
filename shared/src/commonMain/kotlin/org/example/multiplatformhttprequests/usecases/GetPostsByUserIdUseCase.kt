package org.example.multiplatformhttprequests.usecases

import kotlinx.coroutines.flow.Flow
import org.example.multiplatformhttprequests.data.GetPostsByUserId
import org.example.multiplatformhttprequests.data.Post

class GetPostsByUserIdUseCase(private val posts: GetPostsByUserId) {
    fun getPostsByUserId(userId: Int?): Flow<List<Post>> {
        return posts.getPostsByUserIdFlow(userId)
    }
}