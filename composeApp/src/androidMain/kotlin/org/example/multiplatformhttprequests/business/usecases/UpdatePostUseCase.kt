package com.example.bunghttprequests.business.usecases

import org.example.multiplatformhttprequests.data.PostRepository
import org.example.multiplatformhttprequests.data.UpdatePost

class UpdatePostUseCase(private val updatePost: UpdatePost) {
    suspend fun updatePost(post: PostRepository.Post): PostRepository.Post?{
        return updatePost.updatePost(post)
    }
}