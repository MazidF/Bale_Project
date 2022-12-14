package com.example.baleproject.di

import com.example.baleproject.data.model.Issue
import com.example.baleproject.data.model.User
import com.example.baleproject.data.model.UserInfo
import com.example.baleproject.data.remote.api.IssueApi
import com.example.baleproject.data.remote.api.LabelApi
import com.example.baleproject.data.remote.api.UserApi
import com.example.baleproject.data.remote.api.deserializer.IssueDeserializer
import com.example.baleproject.data.remote.api.deserializer.UserDeserializer
import com.example.baleproject.data.remote.api.deserializer.UserInfoDeserializer
import com.example.baleproject.di.qualifiers.Logger
import com.example.baleproject.utils.SERVER_BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(User::class.java, UserDeserializer)
            .registerTypeAdapter(Issue::class.java, IssueDeserializer)
            .registerTypeAdapter(UserInfo::class.java, UserInfoDeserializer)
            .create()
    }

    @Provides
    @Singleton
    @Logger
    fun provideHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Logger logger: Interceptor,
//        @Authorizer authorizer: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
//            .addInterceptor(authorizer)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        client: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SERVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApi(
        retrofit: Retrofit,
    ): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLabelApi(
        retrofit: Retrofit,
    ): LabelApi {
        return retrofit.create(LabelApi::class.java)
    }

    @Provides
    @Singleton
    fun provideIssueApi(
        retrofit: Retrofit,
    ): IssueApi {
        return retrofit.create(IssueApi::class.java)
    }
}