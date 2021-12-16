package com.example.zulipapp.di

import com.example.zulipapp.data.api.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(EMAIL, PASSWORD))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideStreamApi(retrofit: Retrofit): StreamApiService = retrofit.create(StreamApiService::class.java)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApiService = retrofit.create(UserApiService::class.java)

    @Provides
    @Singleton
    fun provideMessageApi(retrofit: Retrofit): MessageApiService = retrofit.create(MessageApiService::class.java)

    @Provides
    @Singleton
    fun provideNetworkStreamDataSource(streamApiService: StreamApiService): NetworkStreamDataSource = NetworkStreamDataSource(streamApiService)

    @Provides
    @Singleton
    fun provideNetworkUserDataSource(userApiService: UserApiService): NetworkUserDataSource = NetworkUserDataSource(userApiService)

    @Provides
    @Singleton
    fun provideNetworkMessageDataSource(messageApiService: MessageApiService): NetworkMessageDataSource = NetworkMessageDataSource(messageApiService)

    companion object{
        private const val BASE_URL = "https://tinkoff-android-fall21.zulipchat.com/api/v1/"
        private const val EMAIL = "e.jolk@yahoo.com"
        private const val PASSWORD = "6MtxtdXVOwu35cHkoiQ0xoog6rOMlyRm"

    }
}