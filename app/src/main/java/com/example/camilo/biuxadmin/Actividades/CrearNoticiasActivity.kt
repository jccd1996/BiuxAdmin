package com.example.camilo.biuxadmin.Actividades

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.camilo.biuxadmin.ConexionWeb.Conexion
import com.example.camilo.biuxadmin.Modelos.Noticia
import com.example.camilo.biuxadmin.R
import com.example.camilo.biuxadmin.Interfaces.ActualizarVista
import com.example.camilo.biuxadmin.Interfaces.AsignarObjetos
import java.util.HashMap

class CrearNoticiasActivity : AppCompatActivity(), AsignarObjetos,
    ActualizarVista {



    var etTitulo: TextView?=null
    var etContenido:TextView?=null
    var etLink:TextView?=null
    var bPublicar: Button?=null

    var noticia: Noticia?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_noticias)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var actionBar = supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)

        asignarObjetos()

        bPublicar?.setOnClickListener {
            datos()
            var conexion= Conexion(this,applicationContext)
            conexion.crearNoticia(noticia!!)
            Toast.makeText(this,"Noticia publicada",Toast.LENGTH_SHORT).show()
            finish()
        }

    }
    override fun asignarObjetos() {
        etTitulo=findViewById(R.id.etTitulo)
        etContenido=findViewById(R.id.etContenido)
        etLink=findViewById(R.id.etLink)
        bPublicar=findViewById(R.id.bPublicar)

    }

    fun datos(){
        var titulo:String=etTitulo?.text.toString()
        var descripcion:String=etContenido?.text.toString()
        var link:String=etLink?.text.toString()

        noticia=Noticia(titulo,descripcion,link)


    }
    override fun actualizar(data: HashMap<*, *>) {

    }

}
