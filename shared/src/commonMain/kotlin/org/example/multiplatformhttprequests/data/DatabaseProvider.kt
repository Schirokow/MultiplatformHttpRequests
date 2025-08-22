package org.example.multiplatformhttprequests.data

import androidx.room.Room
import kotlinx.coroutines.internal.synchronized
import org.example.multiplatformhttprequests.data.dao.PostsDao
import kotlin.concurrent.Volatile

//object DatabaseProvider {
//    @Volatile
//    private var POST_INSTANCE: PostDatabase? = null
//
//    fun getPostDatabase(context: Context): PostDatabase {
//        return POST_INSTANCE ?: synchronized(this) {
//            val instance = Room.databaseBuilder(
//                context.applicationContext,
//                PostDatabase::class.java,
//                "festival_database"
//            )
//                .build()
//            POST_INSTANCE = instance
//            instance
//        }
//    }
//
//    fun providePostDao(context: Context): PostsDao {
//        return getPostDatabase(context).postDao()
//    }
//
//}