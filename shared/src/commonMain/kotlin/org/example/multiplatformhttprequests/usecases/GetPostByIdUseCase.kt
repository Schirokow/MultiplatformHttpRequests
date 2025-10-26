package org.example.multiplatformhttprequests.usecases

import kotlinx.coroutines.flow.Flow
import org.example.multiplatformhttprequests.data.GetPostById
import org.example.multiplatformhttprequests.data.Post


class GetPostByIdUseCase(private val post: GetPostById) {
     fun getPostById(id: Int?): Flow<Post?> {
        return post.getPostByIdFlow(id)
    }
}