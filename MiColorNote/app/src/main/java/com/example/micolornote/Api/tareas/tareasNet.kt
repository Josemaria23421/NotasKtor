package com.example.micolornote.Api.tareas

import UsuarioAPI
import com.example.micolornote.Api.Parametros
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object tareasNet {
    val retrofit: TareasApi by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.FINALURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TareasApi::class.java)
    }
}
