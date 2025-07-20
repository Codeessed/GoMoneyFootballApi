package com.android.goandroiddevelopertest.di

import android.content.Context
import com.android.goandroiddevelopertest.Constants
import com.android.goandroiddevelopertest.data.remotedata.GoApiInterface
import com.android.goandroiddevelopertest.domain.repository.GoRepositoryImpl
import com.android.goandroiddevelopertest.domain.repository.GoRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofitInstance(): Retrofit {
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.base_url)
            .client(client)

        return retrofit.build()
    }

    @Provides
    @Singleton
    fun provideGoModuleApi(retrofit: Retrofit): GoApiInterface {
        return retrofit.create(GoApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun providesGoRepository(goApiInterface: GoApiInterface): GoRepositoryInterface =
        GoRepositoryImpl(goApiInterface)

}