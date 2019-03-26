package com.example.camilo.biuxadmin.Actividades

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Button
import com.example.camilo.biuxadmin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bEmpresas=findViewById<Button>(R.id.bEmpresas)
        val bNoticias=findViewById<Button>(R.id.bNoticias)
        val bUsuarios=findViewById<Button>(R.id.bUsuarios)
        val bGeneral=findViewById<Button>(R.id.bGeneral)
        val bGrupos=findViewById<Button>(R.id.bGrupos)

        val toolbar=findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        bEmpresas.setOnClickListener {
            val intent= Intent(this,EmpresasActivity::class.java)
            startActivity(intent)

        }
        bNoticias.setOnClickListener {
            val intent= Intent(this,NoticiasActivity::class.java)
            startActivity(intent)
        }
        bGeneral.setOnClickListener {
            val intent= Intent(this,GeneralActivity::class.java)
            startActivity(intent)

        }

    }
}
