package org.example.multiplatformhttprequests.usecases

import org.example.multiplatformhttprequests.data.CreatePost
import org.example.multiplatformhttprequests.data.Post
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

//class CreatePostUseCase(): KoinComponent {
//
//    private val createPost: CreatePost by inject()
//
//    suspend fun createNewPost(newPost: Post): Post?{
//        return createPost.createPost(newPost)
//    }
//}

class CreatePostUseCase(private val createPost: CreatePost) {

    suspend fun createNewPost(newPost: Post): Post? {
        return createPost.createPost(newPost)
    }
}