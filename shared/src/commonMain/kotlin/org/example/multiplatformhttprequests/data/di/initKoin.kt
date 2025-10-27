package org.example.multiplatformhttprequests.data.di


import org.example.multiplatformhttprequests.data.CreatePost
import org.example.multiplatformhttprequests.data.CreatePostImpl
import org.example.multiplatformhttprequests.data.GetPostById
import org.example.multiplatformhttprequests.data.GetPostByIdImpl
import org.example.multiplatformhttprequests.data.GetPostsByUserId
import org.example.multiplatformhttprequests.data.GetPostsByUserIdImpl
import org.example.multiplatformhttprequests.data.PostApiService
import org.example.multiplatformhttprequests.usecases.CreatePostUseCase
import org.example.multiplatformhttprequests.usecases.GetPostByIdUseCase
import org.example.multiplatformhttprequests.usecases.GetPostsByUserIdUseCase
import org.example.multiplatformhttprequests.usecases.GetPostsUseCase
import org.example.multiplatformhttprequests.usecases.UpdatePostUseCase
import org.example.multiplatformhttprequests.data.PostsRepository
import org.example.multiplatformhttprequests.data.PostsRepositoryImpl
import org.example.multiplatformhttprequests.data.UpdatePost
import org.example.multiplatformhttprequests.data.UpdatePostImpl
import org.example.multiplatformhttprequests.httpClient
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

// Dies ist die initKoin-Funktion, die von den nativen Plattformen aufgerufen wird.
fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            commonModule,
            platformModule()
        )
    }

// Definiert die gemeinsamen (common) Abhängigkeiten für alle Plattformen
val commonModule = module {

    // Ktor HttpClient
    single { httpClient }

    // Koin soll wissen, wie man PostsRepository bereitstellt
    single<PostsRepository> { PostsRepositoryImpl(get()) }

    single { PostApiService(get()) }

    // Usecases sind plattformunabhängig und verwenden das Repository
    single { GetPostsUseCase(get()) }

    single<CreatePost> { CreatePostImpl(get()) }
    single { CreatePostUseCase(get()) }

    single<UpdatePost>{ UpdatePostImpl(get()) }
    single { UpdatePostUseCase(get()) }

    single <GetPostsByUserId>{ GetPostsByUserIdImpl(get()) }
    single { GetPostsByUserIdUseCase(get()) }

    single<GetPostById> { GetPostByIdImpl(get()) }
    single { GetPostByIdUseCase(get()) }

}

// Erwartete (expect) Funktion für plattformspezifische Module
expect fun platformModule(): org.koin.core.module.Module