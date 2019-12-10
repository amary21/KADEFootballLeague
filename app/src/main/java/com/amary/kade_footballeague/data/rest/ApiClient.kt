package com.amary.kade_footballeague.data.rest

import com.amary.kade_footballeague.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = BuildConfig.BASE_URL
const val ID_LEAGUE = "com.amary.kade_footballeague.ID_LEAGUE"
const val ID_LEAGUE_SAVE = "com.amary.kade_footballeague.ID_LEAGUE_SAVE"
const val ID_EVENT = "com.amary.kade_footballeague.ID_EVENT"
const val ID_TEAMS = "com.amary.kade_footballeague.ID_TEAM"
const val ID_TEAMS_SAVES = "com.amary.kade_footballeague.ID_TEAM_SAVE"
const val NAME_LEAGUE = "com.amary.kade_footballeague.NAME_LEAGUE"
const val IS_NEXT = "com.amary.kade_footballeague.IS_NEXT"
const val IS_PREV = "com.amary.kade_footballeague.IS_PREV"

object ApiClient {

    fun getClient(): ApiInterface {

        val requestInterceptor = Interceptor { chain ->

            val url = chain.request()
                .url
                .newBuilder()
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}