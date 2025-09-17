package org.example.multiplatformhttprequests.data.di

import org.example.multiplatformhttprequests.data.LocalStorageService
import org.example.multiplatformhttprequests.data.PostDatabase
import org.example.multiplatformhttprequests.data.dao.PostsDao
import org.example.multiplatformhttprequests.database.GetiOSDatabase
import org.example.multiplatformhttprequests.viewmodels.PostsViewModel
import org.koin.dsl.module

actual fun platformModule() = module {
    // Hier kannst du iOS-spezifische Abhängigkeiten bereitstellen.
    // Zum Beispiel, wenn du eine iOS-Datenbank hast.
    // iOS DB bereitstellen
    single { GetiOSDatabase.getDatabase() }

    // Dao bereitstellen
    single<PostsDao> { get<PostDatabase>().postDao() }

    // Lokale Datenspeicher-Implementierung, die den Dao benötigt
    single<LocalStorageService.LocalPostsStorage> {
        LocalStorageService.PostsStorageImpl(get())
    }


    // ViewModel registrieren:
    single {
        PostsViewModel(
            getPostsUseCase = get(),
            createPostUseCase = get(),
            updatePostUseCase = get(),
            getPostsByUserIdUseCase = get(),
            getPostByIdUseCase = get(),
            localPostStorage = get()
        )
    }
}