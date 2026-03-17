package com.example.micolornote.Api.admin

import com.example.micolornote.Api.Parametros
import com.example.micolornote.Api.admin.adminApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object adminNet {
    val retrofit: adminApi by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.FINALURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(adminApi::class.java)
    }
}