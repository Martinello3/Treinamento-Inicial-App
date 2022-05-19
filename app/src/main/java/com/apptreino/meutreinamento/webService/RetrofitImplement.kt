package com.apptreino.meutreinamento.webService

import com.apptreino.meutreinamento.BuildConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitImplement() {
    fun buildRetrofit(): Retrofit {
        val lInterceptor = HttpLoggingInterceptor()
        lInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE

        val lBuild = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.HOURS)
            .writeTimeout(60, TimeUnit.HOURS)
            .addInterceptor(lInterceptor)
            .build()


        return Retrofit.Builder()
            .baseUrl("https://treinamento-api-si.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(lBuild)
            .build()
    }
}