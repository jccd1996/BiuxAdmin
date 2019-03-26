package com.example.camilo.biuxadmin.Modelos

class Grupo( ){

    var id: Int=0
    var administradorId: Int=0
    var nombre: String=""
    var descripcion:String=""
    var modalidad:String=""
    var numeroMiembros: Int=0
    var logo: String=""
    var portada: String=""
    var tipo:Boolean=false
    var ciudad:String=""
    var whatsapp:String=""
    var facebook:String=""
    var instagram:String=""

    constructor(id: Int, administradorId: Int,  nombre: String,  descripcion:String , modalidad:String, numeroMiembros: Int,
                logo: String,  portada: String, tipo:Boolean, ciudad:String,  whatsapp:String,
                facebook:String,  instagram:String):this(){

        this.id=id
        this.administradorId=administradorId
        this.nombre=nombre
        this.descripcion=descripcion
        this.modalidad=modalidad
        this.numeroMiembros=numeroMiembros
        this.logo=logo
        this.portada=portada
        this.tipo=tipo
        this.ciudad=ciudad
        this.whatsapp=whatsapp
        this.facebook=facebook
        this.instagram=instagram

    }

}