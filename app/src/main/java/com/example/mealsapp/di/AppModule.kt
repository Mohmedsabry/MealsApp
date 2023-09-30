package com.example.mealsapp.di

import android.app.Application
import androidx.room.Room
import com.example.mealsapp.data.local.RoomDB
import com.example.mealsapp.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun retrofitImp(): ApiService {
        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun roomBuilder(
        context: Application
    ): RoomDB {
        return Room.databaseBuilder(
            context, RoomDB::class.java, "mydb"
        ).build()
    }
}