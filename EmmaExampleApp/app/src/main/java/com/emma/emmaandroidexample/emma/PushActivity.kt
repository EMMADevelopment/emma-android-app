package com.emma.emmaandroidexample.emma

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.emma.emmaandroidexample.R
import io.emma.android.EMMA

class PushActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EMMA.getInstance().checkForRichPushUrl() // Para comprobar el richPushUrl cuando se abre la app desde la notificación
    }
}