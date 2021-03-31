package com.pedrofr.guesstheyear.di

import com.pedrofr.guesstheyear.data.RepositoryImpl
import com.pedrofr.guesstheyear.data.network.mapper.ApiMapper
import com.pedrofr.guesstheyear.data.network.mapper.ApiMapperImpl
import com.pedrofr.guesstheyear.domain.repository.Repository
import com.pedrofr.guesstheyear.ui.GameFactory
import com.pedrofr.guesstheyear.ui.GameFactoryImpl
import com.pedrofr.guesstheyear.ui.MediaPlayer
import com.pedrofr.guesstheyear.ui.MediaPlayerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRepository(
        impl: RepositoryImpl
    ): Repository

    @Binds
    abstract fun providesApiMapper(
        impl: ApiMapperImpl
    ): ApiMapper

    @Binds
    abstract fun provideMediaPlayer(
        impl: MediaPlayerImpl
    ): MediaPlayer

    @Binds
    abstract fun provideGameFactory(
        impl: GameFactoryImpl
    ): GameFactory
}