package org.example.multiplatformhttprequests.usecases

import org.example.multiplatformhttprequests.data.Post
import org.example.multiplatformhttprequests.data.UpdatePost

class UpdatePostUseCase(private val updatePost: UpdatePost) {
    suspend fun updatePost(post: Post): Post?{
        return updatePost.updatePost(post)
    }
}