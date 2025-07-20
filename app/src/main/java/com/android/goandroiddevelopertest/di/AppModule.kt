package com.android.goandroiddevelopertest.di

import android.content.Context
import androidx.room.Room
import com.android.goandroiddevelopertest.Constants
import com.android.goandroiddevelopertest.data.remotedata.GoApiInterface
import com.android.goandroiddevelopertest.db.GoDao
import com.android.goandroiddevelopertest.db.GoDatabase
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
    fun providesGoRepositoryImpl(
        goApiInterface: GoApiInterface,
        goDao: GoDao
    ): GoRepositoryInterface = GoRepositoryImpl(goApiInterface, goDao)


    @Provides
    @Singleton
    fun providesGoDao(goDatabase: GoDatabase) = goDatabase.dao

    @Provides
    @Singleton
    fun provideGoDatabase(
        @ApplicationContext context: Context
    ) =
        Room.databaseBuilder(
            context,
            GoDatabase::class.java,
            "go_database"
        ).build()

}