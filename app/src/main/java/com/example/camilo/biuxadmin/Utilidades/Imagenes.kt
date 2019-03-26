package com.example.camilo.biuxadmin.Utilidades

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream

class Imagenes {
    companion object {
        fun encodeTobase64(image: Bitmap): String {
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b = baos.toByteArray()
            val imageEncoded = Base64.encodeToString(b, Base64.DEFAULT)

            Log.e("LOOK", imageEncoded)
            return imageEncoded
        }
    }

}