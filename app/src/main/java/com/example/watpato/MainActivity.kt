package com.example.watpato

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.example.watpato.home.HomeScreen

class MainActivity : ComponentActivity() {

    // 1) Aquí declaramos la variable para manejar el resultado de la solicitud de permiso.
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // El usuario concedió el permiso de notificaciones
                Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show()
                Log.d("dbug", "Permiso de notificaciones concedido")
            } else {
                // El usuario denegó el permiso de notificaciones
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
                Log.d("dbug", "Permiso de notificaciones denegado")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permiso = Manifest.permission.POST_NOTIFICATIONS

            if (ContextCompat.checkSelfPermission(this, permiso) == PackageManager.PERMISSION_GRANTED) {
                Log.d("dbug", "Permiso de notificación ya concedido")
            } else {
                requestPermissionLauncher.launch(permiso)
            }
        }

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Red,
            ) {
                HomeScreen()
            }
        }
    }
}
