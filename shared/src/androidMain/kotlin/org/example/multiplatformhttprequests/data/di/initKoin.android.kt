package org.example.multiplatformhttprequests.data.di

import org.example.multiplatformhttprequests.data.LocalStorageService
import org.example.multiplatformhttprequests.data.PostDatabase
import org.example.multiplatformhttprequests.data.dao.PostsDao
import org.example.multiplatformhttprequests.database.GetAndroidDatabase
import org.example.multiplatformhttprequests.viewmodels.PostsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

actual fun platformModule() = module {
    // Hier wird die plattformspezifische Datenbank bereitgestellt
    single { GetAndroidDatabase.getDatabase(androidContext()) }

    // PostsDao aus der Datenbank bereitstellen
    single<PostsDao> { get<PostDatabase>().postDao() }

    // Lokale Datenspeicher-Implementierung, die den Dao benötigt
    single<LocalStorageService.LocalPostsStorage> {
        LocalStorageService.PostsStorageImpl(get())
    }

    viewModel {
        PostsViewModel(
            localPostStorage = get(),
            getPostsUseCase = get(),
            createPostUseCase = get(),
            updatePostUseCase = get(),
            getPostsByUserIdUseCase = get(),
            getPostByIdUseCase = get()
        )
    }


}