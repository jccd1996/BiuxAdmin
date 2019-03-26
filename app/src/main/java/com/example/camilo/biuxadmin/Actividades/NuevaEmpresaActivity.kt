package com.example.camilo.biuxadmin.Actividades

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.camilo.biuxadmin.ConexionWeb.Conexion
import com.example.camilo.biuxadmin.Modelos.Empresa
import com.example.camilo.biuxadmin.R
import com.example.camilo.biuxadmin.Interfaces.ActualizarVista
import com.example.camilo.biuxadmin.Interfaces.AsignarObjetos
import com.example.camilo.biuxadmin.Utilidades.Imagenes
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class NuevaEmpresaActivity : AppCompatActivity(), AsignarObjetos,
    ActualizarVista {


    val SOLICITUD_TOMAR_FOTO=1
    val SOLICITUD_SELECCIONAR_FOTO=2

    val permisoReadStorage=android.Manifest.permission.READ_EXTERNAL_STORAGE
    var toolbar: Toolbar?=null
    var id:Int=0
    var etNombre:TextView?=null
    var etCiudad:TextView?=null
    var etDireccion:TextView?=null
    var etDescripcion:TextView?=null
    var etEmail:TextView?=null
    var etCelular:TextView?=null
    var etTelefono:TextView?=null
    var etInstagram:TextView?=null
    var etFb:TextView?=null
    var etLatitud:TextView?=null
    var etLongitud:TextView?=null
    var etHorario:TextView?=null


    var ivPortada:ImageView?=null
    var bmpPortada: Bitmap?=null

    var ivLogoLocal:ImageView?=null
    var bmpLogo:Bitmap?=null

    var empresa: Empresa?=null
    var tipoFoto:Int?=null

    var existeEmpresa:Boolean=false


    var nuevaPortada:Boolean?= false
    var nuevoLogo:Boolean?= false

    var prueba:Boolean?=false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_empresa)



        var intent:Intent=getIntent()
        id=intent.getIntExtra("empresaId",0)

        verificarEmpresa()
        asignarObjetos()
        initToolbar("Nueva Empresa")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun asignarObjetos(){

        etNombre=findViewById(R.id.etNombre)
        etCiudad=findViewById(R.id.etCiudad)
        etDireccion=findViewById(R.id.etDireccion)
        etDescripcion=findViewById(R.id.etDescripcion)
        etEmail=findViewById(R.id.etEmail)
        etCelular=findViewById(R.id.etCelular)
        etTelefono=findViewById(R.id.etTelefono)
        etInstagram=findViewById(R.id.etInstagram)
        etFb=findViewById(R.id.etFb)
        etLatitud=findViewById(R.id.etLatitud)
        etLongitud=findViewById(R.id.etLongitud)
        etHorario=findViewById(R.id.etHorario)

        ivPortada=findViewById(R.id.ivPortadaEmpresa)
        ivLogoLocal=findViewById(R.id.ivLogoLocal)

        ivLogoLocal?.setOnClickListener {
            tipoFoto=2
            seleccionarFoto()
        }
        ivPortada?.setOnClickListener {
            tipoFoto=1
            seleccionarFoto()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nueva_empresa, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            android.R.id.home -> {
                finish()
                return true
            }
            R.id.iCrearEmpresa -> {
                datos()
                if (existeEmpresa){

                    var conexion=Conexion(this,applicationContext)
                    conexion.actualizarEmpresa(empresa!!)
                    Toast.makeText(this,"Empresa actualizada",Toast.LENGTH_SHORT).show()
                    finish()

                }else{

                    var conexion = Conexion(this,applicationContext)
                    conexion.crearEmpresa(empresa!!)
                    Toast.makeText(this,"Empresa creada",Toast.LENGTH_SHORT).show()
                    finish()
                }


                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
    //Parte de seleccionar foto
    @RequiresApi(Build.VERSION_CODES.M)
    fun solicitudPermisosSeleccionarFoto(){
        requestPermissions(arrayOf(permisoReadStorage),SOLICITUD_SELECCIONAR_FOTO)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode){
            SOLICITUD_SELECCIONAR_FOTO->{
                if(grantResults.size>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //disparar intent foto
                    dispararIntentSeleccionarFoto()
                }else{
                    //no tenemos permiso para foto
                    Toast.makeText(this,"No diste permiso para acceder a la galería", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    fun datos(){

        var nombre:String=etNombre?.text.toString()
        var ciudad:String=etCiudad?.text.toString()
        var direccion:String=etDireccion?.text.toString()
        var descripcion:String=etDescripcion?.text.toString()
        var correo:String=etEmail?.text.toString()
        var whatsapp:String=etCelular?.text.toString()
        var telefono:String=etTelefono?.text.toString()
        var instagram:String=etInstagram?.text.toString()
        var fb:String=etFb?.text.toString()
        var latitud:String=etLatitud?.text.toString()
        var longitud:String=etLongitud?.text.toString()
        var horario:String=etHorario?.text.toString()

        var icono:String=""
        if(nuevoLogo!!){
            icono  = Imagenes.encodeTobase64(bmpLogo!!)
        }


        var portada:String=""
        if (nuevaPortada!!){
            portada=Imagenes.encodeTobase64(bmpPortada!!)
        }

        prueba=true

        empresa=Empresa(id,nombre,ciudad,whatsapp,direccion,descripcion,correo,telefono,instagram,fb,latitud,longitud,icono,portada,horario)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            SOLICITUD_SELECCIONAR_FOTO->{

                if(resultCode== Activity.RESULT_OK ){

                    if (tipoFoto==1){
                        nuevaPortada=true
                        bmpPortada=mostrarBitmap(data?.data.toString(),ivPortada!!)


                    }else if(tipoFoto==2){
                        nuevoLogo=true
                        bmpLogo=mostrarBitmap(data?.data.toString(),ivLogoLocal!!)

                    }
                }

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun seleccionarFoto(){
        pedirPermisosSeleccionarFoto()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun pedirPermisosSeleccionarFoto(){
        val deboProveerContexto= ActivityCompat.shouldShowRequestPermissionRationale(this,permisoReadStorage)

        if (deboProveerContexto){
            solicitudPermisosSeleccionarFoto()
        }else{
            solicitudPermisosSeleccionarFoto()
        }
    }
    fun dispararIntentSeleccionarFoto(){
        val intent= Intent(Intent.ACTION_GET_CONTENT)

        intent.setType("image/*")

        startActivityForResult(Intent.createChooser(intent,"Seleccionar una foto"),SOLICITUD_SELECCIONAR_FOTO)
    }

    private fun mostrarBitmap(url:String,foto:ImageView): Bitmap? {
        val uri= Uri.parse(url)
        val stream=contentResolver.openInputStream(uri)
        val imageBitmap= BitmapFactory.decodeStream(stream)
        foto!!.setImageBitmap(imageBitmap)
        return imageBitmap


    }
    fun verificarEmpresa (){
        if (id !=0){
            existeEmpresa=true
            var conexion=Conexion(this,applicationContext)
            conexion.obtenerEmpresa(id)


        }

    }
    override fun actualizar(data: HashMap<*, *>) {
        var datos: String? = null
        try {
            datos = data["EMPRESA"] as String
        } catch (e: Exception) {
            Toast.makeText(this, "empresas", Toast.LENGTH_SHORT).show()
            finish()
        }
        EmpresasActivity.empresasLista = ArrayList<Empresa>()
        if (datos != null) {
            try {


                val nEmpresa = JSONObject(datos)
                id = nEmpresa.getInt("id")
                val nombre = nEmpresa.getString("nombre")
                val ciudad = nEmpresa.getString("ciudad")
                val whatsapp = nEmpresa.getString("whatsapp")
                val direccion = nEmpresa.getString("direccion")
                val descripcion = nEmpresa.getString("descripcion")
                val correoElectronico = nEmpresa.getString("correoElectronico")
                val telefono = nEmpresa.getString("telefono")
                val instagram = nEmpresa.getString("instagram")
                val facebook = nEmpresa.getString("facebook")
                val latitud = nEmpresa.getString("latitud")
                val longitud = nEmpresa.getString("longitud")
                val icono = nEmpresa.getString("icono")
                val portada = nEmpresa.getString("portada")
                val horario=nEmpresa.getString("horario")

                initToolbar(nombre)

                etNombre?.text = nombre
                etCiudad?.text = ciudad
                etDireccion?.text = direccion
                etDescripcion?.text = descripcion
                etEmail?.text = correoElectronico
                etCelular?.text = whatsapp
                etInstagram?.text =instagram
                etFb?.text = facebook
                etLatitud?.text = latitud
                etLongitud?.text = longitud
                etTelefono?.text=telefono
                etHorario?.text=horario

                Picasso.get()
                    .load(Conexion(this,this).completarUrl(icono))
                    //.centerCrop()
                    //.placeholder(R.drawable.cargando)
                    //.error(R.drawable.cargando)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(ivLogoLocal)

                Picasso.get()
                    .load(Conexion(this,this).completarUrl(portada))
                    //.centerCrop()
                    //.placeholder(R.drawable.cargando)
                    //.error(R.drawable.cargando)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(ivPortada)

                empresa=Empresa(id,nombre,ciudad,whatsapp,direccion,descripcion,correoElectronico,telefono,instagram,facebook,latitud,longitud,icono,portada,horario)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
    fun initToolbar(titulo:String){
        toolbar = findViewById(R.id.toolbar)


        toolbar?.title = titulo
        setSupportActionBar(toolbar)

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar?.setNavigationOnClickListener { finish()
        }

    }

}
