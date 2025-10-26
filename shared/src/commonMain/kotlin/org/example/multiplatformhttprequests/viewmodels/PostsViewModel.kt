package org.example.multiplatformhttprequests.viewmodels

import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.launch
import com.rickclephas.kmp.observableviewmodel.launch
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.multiplatformhttprequests.data.LocalStorageService
import org.example.multiplatformhttprequests.data.Post
import org.example.multiplatformhttprequests.logMessage
import org.example.multiplatformhttprequests.usecases.CreatePostUseCase
import org.example.multiplatformhttprequests.usecases.GetPostByIdUseCase
import org.example.multiplatformhttprequests.usecases.GetPostsByUserIdUseCase
import org.example.multiplatformhttprequests.usecases.GetPostsUseCase
import org.example.multiplatformhttprequests.usecases.UpdatePostUseCase


class PostsViewModel(
    private val localPostStorage: LocalStorageService.LocalPostsStorage,
    private val getPostsUseCase: GetPostsUseCase,
    private val createPostUseCase: CreatePostUseCase,
    private val updatePostUseCase: UpdatePostUseCase,
    private val getPostsByUserIdUseCase: GetPostsByUserIdUseCase,
    private val getPostByIdUseCase: GetPostByIdUseCase
): ViewModel() {

//    private val _postsData = MutableStateFlow<List<PostRepository.Post>>(emptyList())
//    val postsData: StateFlow<List<PostRepository.Post>> = _postsData.asStateFlow()

    private val _isLoading = MutableStateFlow(viewModelScope, false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _localStorageState = MutableStateFlow<List<LocalStorageService.LocalPostStorage>>(viewModelScope,emptyList())
    val localStorageState: StateFlow<List<LocalStorageService.LocalPostStorage>> = _localStorageState.asStateFlow()


    init {
        viewModelScope.launch {
            // Posts laden
            getPostsUseCase.getPosts().collect { posts ->
                localPostStorage.insertLocalPosts(posts)
//                _postsData.value = posts
            }

            // Sammle Daten von der lokalen Speicherung
            localPostStorage.getAllLocalPosts().collect { localPosts ->
                _localStorageState.value = localPosts
            }
        }

    }

    fun loadAllPosts(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                getPostsUseCase.getPosts().collect { posts ->
                    localPostStorage.insertLocalPosts(posts)
//                    localPostStorage.getAllLocalPosts().collect { localPosts ->
//                        _localStorageState.value = localPosts
//                    }
                    logMessage("PostsViewModel All Posts loaded")
                }
            } catch (e: Exception){
                logMessage("PostsViewModel Error loading Posts: ${e.message}")
            } finally {
                _isLoading.value = false
            }

        }
    }

    fun loadPostsByUserId(userId: Int?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                getPostsByUserIdUseCase.getPostsByUserId(userId).collect { posts ->
                    localPostStorage.insertLocalPosts(posts)
                    logMessage("PostsViewModel All Posts by userId loaded")
                }
            } catch (e: Exception){
                logMessage("PostsViewModel Error loading Posts by userId: ${e.message}")
            } finally {
                _isLoading.value = false
            }

        }
    }

    fun loadPostById(id: Int?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                getPostByIdUseCase.getPostById(id).collect { post ->
                    val localPost = LocalStorageService.LocalPostStorage(
                        id = post?.id ?: 0,
                        userId = post?.userId ?: 0,
                        title = post?.title ?: "",
                        body = post?.body ?: ""
                    )
                    localPostStorage.insertNewPost(localPost)
                }
                logMessage("PostsViewModel Post by id is loaded")

            } catch (e: Exception){
                logMessage("PostsViewModel Error loading Posts by userId: ${e.message}")
            } finally {
                _isLoading.value = false
            }

        }
    }

    fun insertNewPost(post: LocalStorageService.LocalPostStorage) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                localPostStorage.insertNewPost(post)
                logMessage("PostsViewModel New Post in LocalPostStorage inserted")
            } catch (e: Exception) {
                logMessage("PostsViewModel Error insert new Post in LocalPostStorage: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createNewPost(newPost: Post) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                createPostUseCase.createNewPost(newPost)
                logMessage("PostsViewModel createNewPost function used")
            } catch (e: Exception) {
                logMessage("PostsViewModel Error createNewPost function: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun udatePost(post: Post) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                updatePostUseCase.updatePost(post)
                logMessage("PostsViewModel updatePost function used")
            } catch (e: Exception) {
                logMessage("PostsViewModel Error updatePost function: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }

    }



    fun deleteAllPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                localPostStorage.deleteAllLocalPosts()
                logMessage("PostsViewModel All Posts deleted")
            } catch (e: Exception) {
                logMessage("PostsViewModel Error deleting Posts: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteLocalPostById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                localPostStorage.deleteLocalPostById(id)
                logMessage("PostsViewModel Post by Id:$id deleted")
            } catch (e: Exception) {
                logMessage("PostsViewModel Error deleting Post by Id:$id : ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

}