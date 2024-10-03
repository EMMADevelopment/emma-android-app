package com.emma.emmaandroidexample.emma

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import io.emma.android.EMMA

class PushActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("PushActivity", "onCreate")

        EMMA.getInstance().checkForRichPushUrl() // Para comprobar el richPushUrl cuando se abre la app desde la notificación
        EMMA.getInstance().getNotificationInfo() // Obtener qué se recibe de un Push

        finish()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("PushActivity", "onNewIntent")
        EMMA.getInstance().onNewNotification(intent, true) // Verificará si el usuario ha recibido una notificación cuando la app está abierta
    }
}