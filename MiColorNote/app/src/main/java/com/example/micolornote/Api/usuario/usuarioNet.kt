package com.example.micolornote.Api.usuario

import UsuarioAPI
import com.example.micolornote.Api.Parametros
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object usuarioNet {
    val retrofit: UsuarioAPI by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.FINALURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioAPI::class.java)
    }
}
