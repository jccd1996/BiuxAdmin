package com.example.camilo.biuxadmin.Modelos

import com.google.android.gms.maps.model.LatLng

class Empresa(id:Int,nombre:String, ciudad:String, whatsapp:String/*, foto:Int*/, direccion:String, descripcion:String, correoElectronico:String, telefono:String, instagram:String,
               facebook:String, latitud:String,longitud:String,icono:String,portada:String,horario:String) {
    var id=0
    var nombre=""
    var ciudad=""
    var whatsapp=""
    //var foto=0
    var direccion=""
    var descripcion=""
    var correoElectronico=""
    var telefono=""
    var instagram=""
    var facebook=""
    var latitud=""
    var longitud=""
    var icono=""
    var portada=""
    var horario=""
    //var latLng=""

    init{
        this.id=id
        this.nombre=nombre
        this.ciudad=ciudad
        this.whatsapp=whatsapp
        //this.foto=foto
        this.direccion=direccion
        this.descripcion=descripcion
        this.correoElectronico=correoElectronico
        this.telefono=telefono
        this.instagram=instagram
        this.facebook=facebook
        this.latitud=latitud
        this.longitud=longitud
        this.icono=icono
        this.portada=portada
        this.horario=horario
       // this.latLng=latLng
    }
}