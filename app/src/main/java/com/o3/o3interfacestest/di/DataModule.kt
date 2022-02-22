package com.o3.o3interfacestest.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.o3.o3interfacestest.common.Constants.BASE_URL
import com.o3.o3interfacestest.data.source.local.db.AppDatabase
import com.o3.o3interfacestest.data.source.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @HttpLoggerInterceptorBasic
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }


    @HttpLoggerInterceptorBody
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor1(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }


    @HttpLoggerInterceptorHeader
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor2(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS }


    @Singleton
    @Provides
    fun providesOkHttpClient(@HttpLoggerInterceptorHeader httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun getRoomDbRef(@ApplicationContext context: Context)= Room.databaseBuilder(context, AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .addCallback(
            object : RoomDatabase.Callback() {
            }
        )
        .build()
}