package com.example.camilo.biuxadmin.RecyclerViewEmpresas

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.camilo.biuxadmin.ConexionWeb.Conexion
import com.example.camilo.biuxadmin.Interfaces.ActualizarVista
import com.example.camilo.biuxadmin.Modelos.Empresa
import com.example.camilo.biuxadmin.R
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import java.util.HashMap

class EmpresasAdapter(var contexto: Context, items:ArrayList<Empresa>, var listener: ClickListener/*, var longClickListener:LongClickListener*/):RecyclerView.Adapter<EmpresasAdapter.ViewHolder>(),ActualizarVista{
    override fun actualizar(data: HashMap<*, *>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    var items:ArrayList<Empresa>?=null
    var multiSeleccion=false

    var itemsSeleccionados:ArrayList<Int>?=null
    var viewHolder:ViewHolder?=null

    init{
        this.items=items
        itemsSeleccionados= ArrayList()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista=LayoutInflater.from(contexto).inflate(R.layout.template_empresa,parent,false)
        val viewHolder= ViewHolder(vista, listener/*,longClickListener*/)
        return viewHolder
    }


    override fun getItemCount(): Int {
        return items?.count()!!
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       // val item=items?.get(position)
        var empresa:Empresa=items!![position]
        //holder.foto?.setImageResource(item?.foto!!)
        holder.nombre?.text=empresa?.nombre
        holder.whatsapp?.text=empresa?.whatsapp
        holder.direccion?.text=empresa?.direccion

        Picasso.get()
            .load(Conexion(this,contexto).completarUrl(empresa.icono))
            //.centerCrop()
            //.placeholder(R.drawable.cargando)
            //.error(R.drawable.cargando)
            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .into(holder.foto)


    }

    fun getItem(position: Int): Any {
        //Regresa el objeto entero
        return this.items?.get(position)!!
    }




    class ViewHolder(vista: View,listener: ClickListener/*,longClickListener:LongClickListener*/):RecyclerView.ViewHolder(vista),View.OnClickListener/*,View.OnLongClickListener*/{


        var vista=vista
        var foto:ImageView?=null
        var nombre:TextView?=null
        var whatsapp:TextView?=null
        var direccion:TextView?=null
        var listener: ClickListener?=null
        var longListener:LongClickListener?=null
        init {
            foto=vista.findViewById(R.id.ivLogoLocal)
            nombre=vista.findViewById(R.id.tvNombreEmpresa)
            whatsapp=vista.findViewById(R.id.etCelular)
            direccion=vista.findViewById(R.id.etDireccion)
            this.listener=listener
            this.longListener
            vista.setOnClickListener(this)
           // vista.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!,adapterPosition)
        }
       /* override fun onLongClick(v: View?): Boolean {
            this.longListener?.longClick(v!!,adapterPosition)
            return true
        }*/
    }




}