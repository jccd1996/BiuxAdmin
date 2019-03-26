package com.example.camilo.biuxadmin.Actividades

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.camilo.biuxadmin.RecyclerViewEmpresas.EmpresasAdapter
import com.example.camilo.biuxadmin.ConexionWeb.Conexion
import com.example.camilo.biuxadmin.RecyclerViewEmpresas.ClickListener
import com.example.camilo.biuxadmin.Modelos.Empresa
import com.example.camilo.biuxadmin.R
import com.example.camilo.biuxadmin.Interfaces.ActualizarVista
import com.example.camilo.biuxadmin.RecyclerViewEmpresas.LongClickListener
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap

class EmpresasActivity : AppCompatActivity(), ActualizarVista,ClickListener {
    override fun onClick(vista: View, index: Int) {
        val intent= Intent(applicationContext,DetalleEmpresaActivity::class.java)
        intent.putExtra("empresaId",empresasLista?.get(index)?.id)
        startActivity(intent)
    }

    var listaEmpresas:RecyclerView?=null
    var adaptador: EmpresasAdapter?=null
    var layoutManager:RecyclerView.LayoutManager?=null

    //Toolbar
    var toolbar: Toolbar?=null



    companion object {

        var empresasLista:ArrayList<Empresa>?=null
        var adaptador: EmpresasAdapter?=null

        fun obtenerEmpresa(index:Int):Empresa{
            return adaptador?.getItem(index) as Empresa // Arregla la busqueda cuando se filtra en la lista.

        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresas)


        initToolbar("Empresas")

        val empresas=ArrayList<Empresa>()

        //empresas.add(Empresa("Bicibague","Ibague","3209296233","Mz I Casa 4 Piedra Pintada","Un Grupo donde se vende de todo re makia","jccd1996@hotmail.com","3158433271","www.instagram.com/bicibague","www.facebook.com/bicibague","4.446094",", -75.188228","",""))


        listaEmpresas=findViewById(R.id.ListaEmpresas)
        listaEmpresas?.setHasFixedSize(true)


        layoutManager=LinearLayoutManager(this)
        listaEmpresas?.layoutManager=layoutManager


     //   traerObjetos()

      //  adaptador= EmpresasAdapter(this, empresas, this)
      //  listaEmpresas?.adapter=adaptador





    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_empresa, menu)


        val searchManager=getSystemService(Context.SEARCH_SERVICE) as SearchManager // Activar buscador
        val itemBusqueda=menu?.findItem(R.id.searchView)
        val searchView=itemBusqueda?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint="Buscar empresa..."

        /*searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            //Preparar los datos
        }

        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //Filtrar

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //Filtrar tambien
                //adaptador?.filtrar(newText!!)
                return true
            }


        })*/

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            android.R.id.home -> {
                finish()
                return true
            }
            R.id.iAgregarEmpresa -> {
                val intent= Intent(this,NuevaEmpresaActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
    fun traerObjetos(){
        var conexion= Conexion(this,applicationContext)
        conexion.obtenerEmpresas()
    }
    override fun actualizar(data: HashMap<*, *>) {
        var datos: String? = null
        try {
            datos = data["empresas"] as String
        } catch (e: Exception) {
            Toast.makeText(this, "empresas", Toast.LENGTH_SHORT).show()
            finish()
        }
        empresasLista = ArrayList<Empresa>()
        if (datos != null) {
            try {

                val empresas = JSONArray(datos)
                for (i in 0 until empresas.length()) {
                    val empresa = empresas.getJSONObject(i)
                    var id = empresa.getInt("id")
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
                    val horario:String=empresa.getString("horario")


                    var nEmpresa=Empresa(id,nombre,ciudad,whatsapp,direccion,descripcion,correoElectronico,telefono,instagram,facebook,latitud,longitud,icono,portada,horario)

                    empresasLista?.add(nEmpresa)
                }
                adaptador= EmpresasAdapter(this, empresasLista!!,this)

                var linearLayoutManager=LinearLayoutManager(this)
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
                listaEmpresas?.setLayoutManager(linearLayoutManager)
                listaEmpresas?.setAdapter(adaptador)


            }catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        traerObjetos()
    }

    fun initToolbar(titulo:String){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.title = titulo

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener { finish()
        }

    }

}