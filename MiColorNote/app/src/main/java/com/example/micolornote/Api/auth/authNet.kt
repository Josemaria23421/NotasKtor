package com.example.micolornote.Api.auth

import com.example.micolornote.Api.Parametros
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object authNet {
    val retrofit: AuthAPI by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.FINALURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthAPI::class.java)
    }
}