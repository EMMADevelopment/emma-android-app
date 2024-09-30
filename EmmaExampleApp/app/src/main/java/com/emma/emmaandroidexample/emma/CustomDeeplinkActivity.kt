package com.emma.emmaandroidexample.emma

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.emma.emmaandroidexample.MainActivity
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
        if (uri.host.equals(Routes.DeeplinkScreen.route)) {
            Log.d("CustomDeeplinkActivity", uri.host.toString())
            goDeeplink(uri)
        } else {
            Log.d("CustomDeeplinkActivity", uri.host.toString())
            goDeeplink(uri)
        }
    }

    private fun goDeeplink(uri: Uri) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("DEEPLINK_URL", uri)
        }

        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        EMMA.getInstance().onNewNotification(intent, true)

        if (intent != null && intent.data != null) {
            processDeeplink(intent.data!!)
        }
    }
}