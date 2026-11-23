package com.example.inventarioapp.core.di

import com.example.inventarioapp.feature.book.data.repository.BookRepositoryImpl
import com.example.inventarioapp.feature.book.domain.repository.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBooks(impl: BookRepositoryImpl): BookRepository
}
