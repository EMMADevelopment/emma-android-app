package com.emma.emmaandroidexample.emma

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.emma.emmaandroidexample.R
import io.emma.android.EMMA
import io.emma.android.interfaces.EMMANotificationInterface
import io.emma.android.model.EMMAPushCampaign

class PushActivity : ComponentActivity(), EMMANotificationInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("PushActivity", "onCreate")

        EMMA.getInstance().checkForRichPushUrl() // Para comprobar el richPushUrl cuando se abre la app desde la notificación
        EMMA.getInstance().getNotificationInfo() // Obtener qué se recibe de un Push
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("PushActivity", "onNewIntent")
        EMMA.getInstance().onNewNotification(intent, true) // Verificará si el usuario ha recibido una notificación cuando la app está abierta
    }

    // Controlar qué se recibe de un Push
    override fun onPushOpen(pushCampaign: EMMAPushCampaign) {
        Log.d("SALVA", pushCampaign.message)
    }
}