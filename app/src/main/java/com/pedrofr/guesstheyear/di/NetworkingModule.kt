package com.pedrofr.guesstheyear.di

import com.pedrofr.guesstheyear.data.network.client.DeezerClient
import com.pedrofr.guesstheyear.data.network.client.DeezerService
import com.pedrofr.guesstheyear.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {


    //TODO Add Authorization Interceptor if Token is needed for API

    @Provides
    fun provideClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()
    }

    @Provides
    @Singleton
    fun provideDeezerService(retrofit: Retrofit) = retrofit.create(DeezerService::class.java)

    @Provides
    @Singleton
    fun provideDeezerClient(deezerService: DeezerService) = DeezerClient(deezerService)
}