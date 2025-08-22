package org.example.multiplatformhttprequests.usecases

import kotlinx.coroutines.flow.Flow
import org.example.multiplatformhttprequests.data.GetPostById
import org.example.multiplatformhttprequests.data.PostRepository

class GetPostByIdUseCase(private val post: GetPostById) {
     fun getPostByIdFlow(id: Int?): Flow<PostRepository.Post?> {
        return post.getPostByIdFlow(id)
    }
}