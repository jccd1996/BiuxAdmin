package com.example.camilo.biuxadmin.ConexionWeb

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.camilo.biuxadmin.Modelos.Empresa
import com.example.camilo.biuxadmin.Modelos.Grupo
import com.example.camilo.biuxadmin.Modelos.Noticia
import com.example.camilo.biuxadmin.Modelos.Rodada
import com.example.camilo.biuxadmin.Interfaces.ActualizarVista
import com.example.camilo.biuxadmin.Interfaces.Evento
import org.json.JSONObject
import java.util.HashMap

public class Conexion {
    val ID_LOGIN_WEB = "306637909726-id1duk8188o3js66dbq54rvv67f8b0mf.apps.googleusercontent.com"
    private val CONSULTAR_USUARIO = "/usuarios/consultar"
    private val USUARIO = "usuarios/"
    private val MIEMBROS = "miembros/"
    private val GET = 1
    private val POST = 2
    private val PATCH = 3
    private val DELETE = 4
    private val ERROR = 1
    private val SATISFACTORIO = 2
    private val ERROR_RED = 3

    val URL_BASE = /*"http://192.168.0.12:8084/"*/ "http://biuxv2.bicibague.com/"
    private val URI_API = "api/v1/"
    private val URL = URL_BASE + URI_API//"http://test.bicibague.com/"  "http://186.83.133.13:3001/"
    private val PUNTOS_USUARIO_INSERTAR = "PuntosUsuario/insertar/"
    private val PUNTOS_USUARIO_OBTENER = "PuntosUsuario/verPuntos/"
    private val USUARIO_INSERTAR = "usuarios"
    private val USUARIO_ACTUALIZAR = "usuarios"
    val GRUPOS = "grupos/"
    val RODADAS = "rodadas"
    val USUARIOS = "usuarios"

    private val USUARIO_VERIFICAR_CORREO = "Usuario/verificarCorreo/"


    val RUTAS = "rutas"
    val CATEGORIAS = "categorias"
    val EMPRESAS = "empresas"
    val PUNTOSENCUENTRO = "puntosEncuentro"
    val SITIOS = "sitios"
    val PUBLIC = "public"
    val MONUMENTOS = "monumentos"
    val BICIPARQUEADEROS = "biciparqueaderos"
    val NOTICIAS = "noticias"

    private val ICONO_PNG = "icono.png"
    private val IMAGEN_JPG = "1.jpg"

    fun rutaIcono(id: Int): String {
        return "$URL/$PUBLIC/$EMPRESAS/$id/$ICONO_PNG"

    };
    fun rutaImagen(id: Int): String {
        return "$URL/$PUBLIC/$EMPRESAS/$id/$IMAGEN_JPG"

    };
    fun rutaImagen(categoria: String, id: Int): String {
        return "$URL/$PUBLIC/$categoria/$id/$IMAGEN_JPG"
    }


    private var actividad: ActualizarVista?=null
    private var context: Context?=null

    constructor(actividad: ActualizarVista, context: Context){

            this.actividad = actividad
            this.context = context
    }

    fun completarUrl(url: String): String {
        return URL_BASE + url
    }





    fun obtenerRodadasGrupoId(grupoId: Int) {
        val peticion = "$URL$RODADAS?grupoId=$grupoId"

        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros[RODADAS] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros = HashMap<String, String>()
        ejecutarPeticion(GET, peticion, parametros, null, escuchar)
    }


    fun actualizarGrupo(grupo: Grupo) {

        val peticion = URL + GRUPOS + "?id=" + grupo.id


        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {
                val parametros = HashMap<String, String>()
                parametros["actualizar"] = respuesta
                actividad?.actualizar(parametros)
            }

        }
        val parametros = HashMap<String, String>()
        //      parametros.put("id",grupo.getId()+"");
        parametros["nombre"] = grupo.nombre
        //parametros.put("administrador",grupo.getAdministradorId()+"");
        parametros["descripcion"] = grupo.nombre
        parametros["modalidad"] = grupo.modalidad
        parametros["numeroMiembros"] = grupo.numeroMiembros.toString()

        parametros["logo"] = grupo.logo
        parametros["portada"] = grupo.portada
        parametros["tipo"] = grupo.tipo.toString()
        parametros["ciudad"] = grupo.ciudad
        parametros["whatsapp"] = grupo.whatsapp
        parametros["facebook"] = grupo.facebook
        parametros["instagram"] = grupo.instagram



        ejecutarPeticion(PATCH, peticion, parametros, null, escuchar)

    }

    fun crearRodada(rodada: Rodada) {

        val peticion = URL + RODADAS


        val escuchar = object : Evento {

            override fun escuchar(codigo: Int, respuesta: String) {
                val parametros = HashMap<String, String>()
                parametros["respuesta"] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros = HashMap<String, String>()
        parametros["nombre"] = rodada.nombre
        parametros["fechaHora"] = rodada.fechaHora
        parametros["ruta"] = rodada.ruta
        parametros["puntoEncuentro"] = rodada.puntoEncuentro
        parametros["numLikes"] = rodada.numLikes.toString()
        parametros["logo"] = rodada.imagen
        parametros["distancia"] = rodada.km
        parametros["descripcion"] = rodada.drescripcion
        parametros["modalidad"] = rodada.modalidad
        parametros["grupoId"] = rodada.grupoID.toString()



        ejecutarPeticion(POST, peticion, parametros, null, escuchar)

    }

    fun obtenerRodadas() {

        val peticion = URL + RODADAS

        val escuchar = object : Evento {

            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros[RODADAS] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros = HashMap<String, String>()
        ejecutarPeticion(GET, peticion, parametros, null, escuchar)
    }

    fun obtenerGrupos() {

        val peticion = URL + GRUPOS

        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros[GRUPOS] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros = HashMap<String, String>()
        ejecutarPeticion(GET, peticion, parametros, null, escuchar)
    }

    fun usuariosGrupo(grupoId: Int) {
        val peticion = "$URL$GRUPOS$grupoId/$MIEMBROS"
        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros[USUARIOS] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros = HashMap<String, String>()
        ejecutarPeticion(GET, peticion, parametros, null, escuchar)
    }


    fun eliminarGrupo(id: Int) {

        val peticion = "$URL$GRUPOS$id/"

        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros["ELIMINAR"] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros = HashMap<String, String>()
        ejecutarPeticion(DELETE, peticion, parametros, null, escuchar)


    }


    fun obtenerEmpresas() {

        val peticion = URL + EMPRESAS

        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros[EMPRESAS] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros = HashMap<String, String>()
        ejecutarPeticion(GET, peticion, parametros, null, escuchar)
    }
    fun obtenerEmpresa(id: Int){
        val peticion = URL+EMPRESAS+"/"+id

        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros["EMPRESA"] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros = HashMap<String, String>()
        ejecutarPeticion(GET, peticion, parametros, null, escuchar)

    }
    fun actualizarEmpresa(empresa: Empresa){
        val peticion: String= URL+ EMPRESAS +"/"+empresa.id+"?tipo=iconoFoto"

        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros[EMPRESAS] = respuesta
                actividad?.actualizar(parametros)
            }

        }
        val parametros = HashMap<String, String>()
        parametros["id"]=empresa.id.toString()
        parametros["nombre"]=empresa.nombre
        parametros["ciudad"]=empresa.ciudad
        parametros["whatsapp"]=empresa.whatsapp
        //parametros["foto"]=empresa.foto
        parametros["direccion"]=empresa.direccion
        parametros["descripcion"]=empresa.descripcion
        parametros["correoElectronico"]=empresa.correoElectronico
        parametros["telefono"]=empresa.telefono
        parametros["instagram"]=empresa.instagram
        parametros["facebook"]=empresa.facebook
        parametros["latitud"]=empresa.latitud
        parametros["longitud"]=empresa.longitud
        parametros["icono"]=empresa.icono
        parametros["portada"]=empresa.portada
        parametros["horario"]=empresa.horario
        ejecutarPeticion(PATCH, peticion, parametros, null, escuchar)

    }


    fun crearEmpresa(empresa: Empresa){

        val peticion: String= URL+ EMPRESAS +"?tipo=iconoFoto"

        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros[EMPRESAS] = respuesta
                actividad?.actualizar(parametros)
            }

        }
        val parametros = HashMap<String, String>()
        //parametros["id"]=empresa.id.toString()
        parametros["nombre"]=empresa.nombre
        parametros["ciudad"]=empresa.ciudad
        parametros["whatsapp"]=empresa.whatsapp
        //parametros["foto"]=empresa.foto
        parametros["direccion"]=empresa.direccion
        parametros["descripcion"]=empresa.descripcion
        parametros["correoElectronico"]=empresa.correoElectronico
        parametros["telefono"]=empresa.telefono
        parametros["instagram"]=empresa.instagram
        parametros["facebook"]=empresa.facebook
        parametros["latitud"]=empresa.latitud
        parametros["longitud"]=empresa.longitud
        parametros["icono"]=empresa.icono
        parametros["portada"]=empresa.portada
        parametros["horario"]=empresa.horario
        ejecutarPeticion(POST, peticion, parametros, null, escuchar)

    }

    fun eliminarEmpresa(id: Int) {

        val peticion = URL+EMPRESAS+"/"+id

        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros["ELIMINAR"] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros = HashMap<String, String>()
        ejecutarPeticion(DELETE, peticion, parametros, null, escuchar)


    }

    fun crearNoticia(noticia: Noticia){
        val peticion: String= URL+NOTICIAS

        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros[NOTICIAS] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros=HashMap<String,String>()
        parametros["titulo"]=noticia.titulo
        parametros["descripcion"]=noticia.descripcion
        parametros["activo"]=true.toString()
        parametros["link"]=noticia.link
        ejecutarPeticion(POST, peticion, parametros, null, escuchar)
    }

    fun obtenerNoticias() {

        val peticion = URL + NOTICIAS

        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros[NOTICIAS] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros = HashMap<String, String>()
        ejecutarPeticion(GET, peticion, parametros, null, escuchar)
    }

    fun obtenerUsuario(correoElectronico: String) {

        val peticion = URL + USUARIOS

        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros["USUARIO_OBTENIDO"] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros = HashMap<String, String>()
        parametros["correoElectronico"] = correoElectronico
        ejecutarPeticion(GET, peticion, parametros, null, escuchar)
    }

    fun buscarGrupoAdminId(administradorId: Int) {

        val peticion = URL + GRUPOS

        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros["GRUPO"] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros = HashMap<String, String>()
        parametros["administrador"] = administradorId.toString() + ""
        ejecutarPeticion(GET, peticion, parametros, null, escuchar)
    }

    fun obtenerGrupoId(grupoId: Int) {

        val peticion = URL + GRUPOS

        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros["GRUPO"] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros = HashMap<String, String>()
        parametros["id"] = grupoId.toString() + ""
        ejecutarPeticion(GET, peticion, parametros, null, escuchar)
    }

    fun obtenerAdministradorGrupo(usuarioId: Int) {
        val peticion = URL + USUARIO + usuarioId

        val escuchar = object : Evento {
            override fun escuchar(codigo: Int, respuesta: String) {

                val parametros = HashMap<String, String>()
                parametros["USUARIO"] = respuesta
                actividad?.actualizar(parametros)
            }

        }

        val parametros = HashMap<String, String>()
        // parametros.put("id",usuarioId+"");
        ejecutarPeticion(GET, peticion, parametros, null, escuchar)

    }



    private fun ejecutarPeticion(
        tipo: Int,
        url: String,
        parametros: Map<String, String>?,
        headers: Map<String, String>?,
        escuchar: Evento
    ) {
        var url = url
        val metodo: Int
        when (tipo) {
            GET -> {
                metodo = Request.Method.GET
                url = "$url?"
                for ((key, value) in parametros!!) {
                    url += "$key=$value&"


                }
            }
            POST -> metodo = Request.Method.POST
            PATCH -> metodo = Request.Method.PATCH
            DELETE -> metodo = Request.Method.DELETE
            else -> metodo = 1
        }//algspldmgpskpsm url+=pasarDatos(parametros);
        println("la Url es:$url")

        val stringRequest = object : StringRequest(metodo, url,
            Response.Listener { response -> escuchar.escuchar(SATISFACTORIO, response) },
            Response.ErrorListener { error -> escuchar.escuchar(ERROR, error.message!!) }) {
            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                var response = response
                val datos = ""
                if (response.headers == null) {
                    // cant just set a new empty map because the member is final.


                    response = NetworkResponse(
                        response.statusCode,
                        response.data,
                        emptyMap(), // this is the important line, set an empty but non-null map.
                        response.notModified,
                        response.networkTimeMs
                    )
                }
                println("88888888888888888888888888888888888888888888885555555555555555555555555555555555555555555555555555555555555555555555555551111111111111111111111113333333333333333333333344444444444444444444444666666666666666666")

                return super.parseNetworkResponse(response)
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {
                return if (parametros == null) {
                    null
                } else {
                    JSONObject(parametros).toString().toByteArray()
                }

            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        ejecutar(stringRequest)

    }

    private fun ejecutar(stringRequest: StringRequest) {
        val queue = Volley.newRequestQueue(context)
        queue.add(stringRequest)
    }

}