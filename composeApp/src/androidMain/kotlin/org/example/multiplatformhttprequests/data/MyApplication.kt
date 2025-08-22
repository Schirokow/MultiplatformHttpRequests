package org.example.multiplatformhttprequests.data

import android.app.Application
import org.example.multiplatformhttprequests.data.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Starte Koin aus commonMain
        initKoin {
            // Plattformspezifische Konfiguration hinzufügen
            androidLogger()
            androidContext(this@MyApplication)
        }
    }
}

