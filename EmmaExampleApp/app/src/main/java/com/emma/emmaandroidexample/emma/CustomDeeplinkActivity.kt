package com.emma.emmaandroidexample.emma

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.emma.emmaandroidexample.ui.navigation.Routes
import io.emma.android.EMMA

class CustomDeeplinkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("CustomDeeplinkActivity", "onCreate")

        EMMA.getInstance().checkForRichPushUrl()

        if (intent != null && intent.data != null) {
            processDeeplink(intent.data!!)
        }

        finish()
    }

    private fun processDeeplink(uri: Uri) {
        // Aquí podemos gestionar nuestro deeplink cómo queramos dependiendo, en este caso, del host de la uri
        Log.d("CustomDeeplinkActivity", uri.host.toString())
        if (uri.host.equals(Routes.DeeplinkScreen.route)) {
            Log.d("CustomDeeplinkActivity", uri.host.toString())
            goDeeplink(uri)
        } else {
            Log.d("CustomDeeplinkActivity", uri.host.toString())
            goDeeplink(uri)
        }
    }

    private fun goDeeplink(uri: Uri) {
        // Aquí decidimos la navegación
        Log.d("CustomDeeplinkActivity", "Se va a actualizar a: $uri")
        val deeplink = uri.toString()
        val intent = Intent("UPDATE_DEEPLINK")
        intent.putExtra("DEEPLINK", deeplink)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        EMMA.getInstance().onNewNotification(intent, true)

        if (intent != null && intent.data != null) {
            processDeeplink(intent.data!!)
        }
    }
}