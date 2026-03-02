package com.example.micolornote.Api.notas

import com.example.micolornote.Api.Parametros
import com.example.micolornote.Api.tareas.TareasApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object notasNet {
    val retrofit: NotasApi by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.FINALURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NotasApi::class.java)
    }
}
