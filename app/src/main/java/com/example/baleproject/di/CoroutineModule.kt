package com.example.baleproject.di

import com.example.baleproject.di.qualifiers.Default
import com.example.baleproject.di.qualifiers.IO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoroutineModule {

    @Provides
    @Singleton
    @IO
    fun provideDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    @Default
    fun provideDispatcherDefault(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}