package com.pedrofr.guesstheyear.di

import com.pedrofr.guesstheyear.data.model.QuestionRepository
import com.pedrofr.guesstheyear.data.model.QuestionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRepository(
        impl: QuestionRepositoryImpl
    ) : QuestionRepository
}