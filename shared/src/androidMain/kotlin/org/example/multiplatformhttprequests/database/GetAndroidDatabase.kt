package org.example.multiplatformhttprequests.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.multiplatformhttprequests.data.PostDatabase

object GetAndroidDatabase {

    fun getDatabase(context: Context): PostDatabase {
        val dbFile = context.getDatabasePath("local_posts")
        return Room.databaseBuilder<PostDatabase>(
            context = context.applicationContext,
            name = dbFile.absolutePath
        )
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}