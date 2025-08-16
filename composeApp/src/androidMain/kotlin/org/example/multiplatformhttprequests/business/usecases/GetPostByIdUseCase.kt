package com.example.bunghttprequests.business.usecases

import org.example.multiplatformhttprequests.data.GetPostById
import org.example.multiplatformhttprequests.data.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostByIdUseCase(private val post: GetPostById) {
     fun getPostByIdFlow(id: Int?): Flow<PostRepository.Post?> {
        return post.getPostByIdFlow(id)
    }
}