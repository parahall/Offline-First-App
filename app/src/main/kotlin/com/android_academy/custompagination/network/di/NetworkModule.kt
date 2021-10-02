package com.android_academy.custompagination.network.di

import com.android_academy.custompagination.network.NetworkSource
import com.android_academy.custompagination.network.NetworkSourceImpl
import com.android_academy.custompagination.network.StarWarsApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
object NetworkModule {

    private const val BASE_URL = "https://swapi.dev/api/"

    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor).readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    fun provideStarWarsApi(retrofit: Retrofit): StarWarsApi {
        return retrofit.create(StarWarsApi::class.java)
    }

    @Provides
    fun provideNetworkSource(api: StarWarsApi): NetworkSource {
        return NetworkSourceImpl(api)
    }
}