package com.pedrofr.guesstheyear.di

import com.pedrofr.guesstheyear.domain.repository.Repository
import com.pedrofr.guesstheyear.data.RepositoryImpl
import com.pedrofr.guesstheyear.data.network.mapper.ApiMapper
import com.pedrofr.guesstheyear.data.network.mapper.ApiMapperImpl
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
    ) : Repository

    @Binds
    abstract fun providesApiMapper(
        impl: ApiMapperImpl
    ) : ApiMapper
}