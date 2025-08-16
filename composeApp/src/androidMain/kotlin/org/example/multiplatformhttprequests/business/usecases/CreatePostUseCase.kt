package com.example.bunghttprequests.business.usecases

import org.example.multiplatformhttprequests.data.CreatePost
import org.example.multiplatformhttprequests.data.PostRepository

class CreatePostUseCase(private val createPost: CreatePost) {

    suspend fun createNewPost(newPost: PostRepository.Post): PostRepository.Post?{
        return createPost.createPost(newPost)
    }
}