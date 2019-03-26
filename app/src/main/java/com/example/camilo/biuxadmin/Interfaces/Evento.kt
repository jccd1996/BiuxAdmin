package com.example.camilo.biuxadmin.Interfaces

interface Evento {

    abstract fun escuchar(codigo: Int, respuesta: String)
}