package org.example.multiplatformhttprequests.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


//fun postsDataFlow(): Flow<List<Post>> = flow {
//    emit(getPosts())
//}
//
//fun getPostsByIdFlow(userId: Int?): Flow<List<Post>> = flow {
//    emit(getPostsByUserId(userId))
//}
//
//fun getPostByIdFlow1(id: Int?): Flow<Post?> = flow {
//    emit(getPostById(id))
//}


interface PostsRepository{
    fun getPostsFlow(): Flow<List<Post>>
}

interface GetPostsByUserId{
    fun getPostsByUserIdFlow(userId: Int?): Flow<List<Post>>
}

interface GetPostById{
    fun getPostByIdFlow(id: Int?): Flow<Post?>
}

interface CreatePost{
    suspend fun createPost(newPost: Post): Post?
}

interface UpdatePost{
    suspend fun updatePost(post: Post): Post?
}

class PostsRepositoryImpl(private val api: PostApiService): PostsRepository{
    override fun getPostsFlow(): Flow<List<Post>> {
        return flow {
            emit(api.getPosts())
        }
    }


}

class GetPostsByUserIdImpl(private val api: PostApiService): GetPostsByUserId{
    override fun getPostsByUserIdFlow(userId: Int?): Flow<List<Post>> {
        return flow {
            emit(api.getPostsByUserId(userId))
        }
    }


}

class GetPostByIdImpl(private val api: PostApiService): GetPostById{
    override fun getPostByIdFlow(id: Int?): Flow<Post?> {
        return flow {
            emit(api.getPostById(id))
        }
    }
}

class CreatePostImpl(private val api: PostApiService): CreatePost{
    override suspend fun createPost(newPost: Post): Post? {
        return api.createPost(newPost)
    }
}

class UpdatePostImpl(private val api: PostApiService): UpdatePost{
    override suspend fun updatePost(post: Post): Post? {
        return api.updatePost(post)
    }
}