package com.pedrofr.guesstheyear.di

import android.content.Context
import com.pedrofr.guesstheyear.data.db.AppDatabase
import com.pedrofr.guesstheyear.data.db.dao.TracksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModel {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }


    @Provides
    fun provideTracksDao(appDatabase: AppDatabase): TracksDao {
        return appDatabase.tracksDao()
    }




}