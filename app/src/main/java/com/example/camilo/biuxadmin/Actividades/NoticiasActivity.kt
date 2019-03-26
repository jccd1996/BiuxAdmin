package com.example.camilo.biuxadmin.Actividades

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.example.camilo.biuxadmin.R

class NoticiasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)

        initToolbar("Noticias")


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_empresa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            android.R.id.home -> {
                finish()
                return true
            }
            R.id.iAgregarEmpresa -> {
                val intent= Intent(this,CrearNoticiasActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
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
