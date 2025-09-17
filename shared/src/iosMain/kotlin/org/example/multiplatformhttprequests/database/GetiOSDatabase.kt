package org.example.multiplatformhttprequests.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.multiplatformhttprequests.data.PostDatabase
import platform.Foundation.NSHomeDirectory

object GetiOSDatabase{
    fun getDatabase(): PostDatabase {
        val dbFile = NSHomeDirectory() + "/local_posts"
        return Room.databaseBuilder<PostDatabase>(
            name = dbFile,
//        factory = { PostDatabase::class.instantiateImpl() }
        )
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}

