package com.example.mealsapp.di

import com.example.mealsapp.data.repository.RepositoryImp
import com.example.mealsapp.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    @Singleton
    abstract fun convertRepo(
        repositoryImp: RepositoryImp
    ): Repository
}