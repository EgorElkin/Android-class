package com.example.zulipapp.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://tinkoff-android-fall21.zulipchat.com/api/v1/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(BasicAuthInterceptor())
        .build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    val streamApiService: StreamApiService = getRetrofit().create(StreamApiService::class.java)
    val userApiService: UserApiService = getRetrofit().create(UserApiService::class.java)
    val messageApiService: MessageApiService = getRetrofit().create(MessageApiService::class.java)
}