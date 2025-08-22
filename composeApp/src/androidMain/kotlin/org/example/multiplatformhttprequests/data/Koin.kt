package org.example.multiplatformhttprequests.data

import org.example.multiplatformhttprequests.usecases.CreatePostUseCase
import org.example.multiplatformhttprequests.usecases.GetPostByIdUseCase
import org.example.multiplatformhttprequests.usecases.GetPostsByUserIdUseCase
import org.example.multiplatformhttprequests.usecases.GetPostsUseCase
import org.example.multiplatformhttprequests.usecases.UpdatePostUseCase
import org.example.multiplatformhttprequests.viewmodels.PostsViewModel
import org.example.multiplatformhttprequests.data.dao.PostsDao
import org.example.multiplatformhttprequests.database.GetAndroidDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Koin soll wissen, wie man PostsDao bereitstellt
    // Dies hängt vom Android Context ab, den Koin mit androidContext() liefert
//    single { DatabaseProvider.providePostDao(androidContext()) }
    single { GetAndroidDatabase.getDatabase(androidContext()) }

    // PostsDao aus PostDatabase bereitstellen
    single<PostsDao> { get<PostDatabase>().postDao() }

    // Koin soll wissen, wie man LocalStorageService.LocalPostsStorage bereitstellt
    // Es benötigt ein PostsDao, das Koin jetzt bereitstellen kann
    single<LocalStorageService.LocalPostsStorage> {
        LocalStorageService.PostsStorageImpl(get())
    }


    // Rezept 1: "Wenn jemand ein PostsRepository will, gib ihm EINE EINZIGE Instanz
    // von PostsRepositoryImplFlow."
    // Wir binden das Interface an die konkrete Implementierung.
    single<PostsRepository> { PostsRepositoryImplFlow() }

    // Rezept für GetPostsUseCase
    // Es benötigt ein PostsRepository, das Koin bereits kennt
    single { GetPostsUseCase(get()) }

    single<CreatePost> { CreatePostImpl() }

    // Rezept für CreatePostUseCase
    // Es benötigt ein PostsRepository, das Koin bereits kennt
    single { CreatePostUseCase(get()) }

    single<UpdatePost>{ UpdatePostImpl() }

    single { UpdatePostUseCase(get()) }

    single <GetPostsByUserId>{ GetPostsByUserIdImplFlow() }

    single { GetPostsByUserIdUseCase(get()) }

    single<GetPostById> { GetPostByIdImplFlow() }

    single { GetPostByIdUseCase(get()) }

    // Rezept 2: "Wenn jemand ein PostsViewModel will, erstelle es.
    // Für die Abhängigkeiten im Konstruktor, nimm das, was du kennst."
    // 'get()' sagt Koin: "Finde eine Abhängigkeit (hier: LocalStorageService.LocalPostsStorage und GetPostsUseCase),
    // die du bereits in deinen Modulen definiert hast, und setze sie hier ein."
    viewModel {
        PostsViewModel(
            localPostStorage = get(), // Koin findet die bereitgestellte LocalPostsStorage-Instanz
            getPostsUseCase = get(), // Koin findet die bereitgestellte GetPostsUseCase-Instanz
            createPostUseCase = get(),
            updatePostUseCase = get(),
            getPostsByUserIdUseCase = get(),
            getPostByIdUseCase = get()
        )
    }

    // Rezept 2: "Wenn jemand ein PostsViewModel will, erstelle es.
    // Für die Abhängigkeiten im Konstruktor, nimm das, was du kennst."
    // 'get()' sagt Koin: "Finde eine Abhängigkeit (hier: PostsRepository),
    // die du bereits in deinen Modulen definiert hast, und setze sie hier ein."
//    viewModel { PostsViewModel(
//        get(),
//        getPostsUseCase = get()
//    ) }
}