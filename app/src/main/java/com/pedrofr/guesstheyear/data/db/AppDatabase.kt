package com.pedrofr.guesstheyear.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pedrofr.guesstheyear.data.db.dao.TracksDao
import com.pedrofr.guesstheyear.data.db.entities.DbTracks
import com.pedrofr.guesstheyear.util.DATABASE_NAME

/**
 * SQLite Database for storing the logs.
 */
@Database(entities = [DbTracks::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tracksDao(): TracksDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
//                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
//                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }
}