package com.emma.emmaandroidexample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.emma.emmaandroidexample.emma.PushActivity
import com.emma.emmaandroidexample.ui.navigation.EmmaAndroidExampleNavigation
import com.emma.emmaandroidexample.ui.navigation.Routes
import com.emma.emmaandroidexample.ui.theme.EmmaAndroidExampleTheme
import io.emma.android.EMMA
import io.emma.android.interfaces.EMMANotificationInterface
import io.emma.android.model.EMMAPushCampaign
import io.emma.android.model.EMMAPushOptions

class MainActivity : ComponentActivity(), EMMANotificationInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // INICIALIZACIÓN DE EMMA
        val configuration = EMMA.Configuration.Builder(this)
            .setSessionKey("")
            .setDebugActive(BuildConfig.DEBUG)
            .build()

        EMMA.getInstance().startSession(configuration)

        // SOLICTAR PERMISO AL USAURIO PARA RECIBIR NOTIFICACIONES
        EMMA.getInstance().requestNotificationPermission()

        // INICIALIZACIÓN DEL SISTEMA DE PUSH
        val pushOpt = EMMAPushOptions.Builder(PushActivity::class.java, R.drawable.ic_launcher_foreground)
            .setNotificationColor(ContextCompat.getColor(this, R.color.purple_200))
            .setNotificationChannelName("My custom channel")
            .build()

        EMMA.getInstance().startPushSystem(pushOpt)

        setContent {
            EmmaAndroidExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val deeplinkUrl = intent?.getStringExtra("DEEPLINK_URL")

                    LaunchedEffect(deeplinkUrl) {
                        navController.navigate(Routes.HomeScreen.createRouteWithDeeplink(deeplinkUrl ?: "Deeplink did not arrive"))
                    }

                    EmmaAndroidExampleNavigation(navController = navController)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        EMMA.getInstance().onNewNotification(intent, true) // Verificará si el usuario ha recibido una notificación cuando la app esta abierta
    }

    override fun onPushOpen(pushCampaign: EMMAPushCampaign) {
        Log.d("SALVA", pushCampaign.message)
    }
}
