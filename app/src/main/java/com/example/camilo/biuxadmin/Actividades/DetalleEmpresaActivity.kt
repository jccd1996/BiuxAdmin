package com.example.camilo.biuxadmin.Actividades

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.camilo.biuxadmin.ConexionWeb.Conexion
import com.example.camilo.biuxadmin.Interfaces.ActualizarVista
import com.example.camilo.biuxadmin.Modelos.Empresa
import com.example.camilo.biuxadmin.R
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class DetalleEmpresaActivity : AppCompatActivity(),ActualizarVista {

    var toolbar: Toolbar?=null
    val index: Int = 0
    var id:Int=0
    var tvNombre: TextView? = null
    var tvCiudad: TextView? = null
    var tvDireccion: TextView? = null
    var tvDescripcion: TextView? = null
    var tvEmail: TextView? = null
    var tvCelular: TextView? = null
    var tvInstagram: TextView? = null
    var tvFb: TextView? = null
    var tvLatitud: TextView? = null
    var tvLongitud: TextView? = null
    var tvTelefono:TextView?=null
    var tvHorario:TextView?=null
    var ivIcono:ImageView?=null
    var ivPortada:ImageView?=null
    var nombreEmpresa:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_empresa)



        var intent:Intent=getIntent()
        // rodada=intent.getSerializableExtra("rodada") as Rodada
        id=intent.getIntExtra("empresaId",0)

        mapearDatos()
        traerObjetos()




    }

    fun mapearDatos() {
        //val empresa=EmpresasActivity.obtenerEmpresa(index)
        tvNombre = findViewById<TextView>(R.id.etNombre)
        tvCiudad = findViewById<TextView>(R.id.etCiudad)
        tvDireccion = findViewById<TextView>(R.id.etDireccion)
        tvDescripcion = findViewById<TextView>(R.id.etDescripcion)
        tvEmail = findViewById<TextView>(R.id.etEmail)
        tvCelular = findViewById<TextView>(R.id.etCelular)
        tvInstagram = findViewById<TextView>(R.id.etInstagram)
        tvFb = findViewById<TextView>(R.id.etFb)
        tvLatitud = findViewById<TextView>(R.id.tvLatitud)
        tvLongitud = findViewById<TextView>(R.id.etLongitud)
        tvTelefono=findViewById(R.id.tvTelefono)
        ivIcono=findViewById<ImageView>(R.id.ivIcono)
        ivPortada=findViewById(R.id.ivPortada)
        tvHorario=findViewById(R.id.tvHorario)



        /*tvNombre.text=empresa.nombre
        tvCiudad.text=empresa.ciudad
        tvDireccion.text=empresa.direccion
        tvDescripcion.text=empresa.descripcion
        tvEmail.text=empresa.correoElectronico
        tvCelular.text=empresa.whatsapp
        tvInstagram.text=empresa.instagram
        tvFb.text=empresa.facebook
        tvLatitud.text=empresa.latitud
        tvLongitud.text=empresa.longitud*/


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalle_empresa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            android.R.id.home -> {
                finish()
                return true
            }
            R.id.iEliminar -> {
                var conexion=Conexion(this,applicationContext)
                conexion.eliminarEmpresa(id)
                //Persitencia.setGrupoId(applicationContext,0)
                Toast.makeText(this,"Empresa eliminada",Toast.LENGTH_SHORT).show()
                finish()
                return true

            }
            R.id.iEditar -> {
                val intent = Intent(this,NuevaEmpresaActivity::class.java)
                intent.putExtra("empresaId", id)
                startActivity(intent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // mapearDatos()
    }

    fun traerObjetos() {
        var conexion = Conexion(this, applicationContext)
        conexion.obtenerEmpresa(id)
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


                        val empresa = JSONObject(datos)
                         id = empresa.getInt("id")
                        val nombre = empresa.getString("nombre")
                        val ciudad = empresa.getString("ciudad")
                        val whatsapp = empresa.getString("whatsapp")
                        val direccion = empresa.getString("direccion")
                        val descripcion = empresa.getString("descripcion")
                        val correoElectronico = empresa.getString("correoElectronico")
                        val telefono = empresa.getString("telefono")
                        val instagram = empresa.getString("instagram")
                        val facebook = empresa.getString("facebook")
                        val latitud = empresa.getString("latitud")
                        val longitud = empresa.getString("longitud")
                        val icono = empresa.getString("icono")
                        val portada = empresa.getString("portada")
                        val horario=empresa.getString("horario")

                        initToolbar(nombre)

                        tvNombre?.text = nombre
                        tvCiudad?.text = ciudad
                        tvDireccion?.text = direccion
                        tvDescripcion?.text = descripcion
                        tvEmail?.text = correoElectronico
                        tvCelular?.text = whatsapp
                        tvInstagram?.text = instagram
                        tvFb?.text = facebook
                        tvLatitud?.text = latitud
                        tvLongitud?.text = longitud
                        tvTelefono?.text=telefono
                        tvHorario?.text=horario





                        Picasso.get()
                            .load(Conexion(this,this).completarUrl(icono))
                            //.centerCrop()
                            //.placeholder(R.drawable.cargando)
                            //.error(R.drawable.cargando)
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                            .into(ivIcono)

                        Picasso.get()
                            .load(Conexion(this,this).completarUrl(portada))
                            //.centerCrop()
                            //.placeholder(R.drawable.cargando)
                            //.error(R.drawable.cargando)
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                            .into(ivPortada)


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

