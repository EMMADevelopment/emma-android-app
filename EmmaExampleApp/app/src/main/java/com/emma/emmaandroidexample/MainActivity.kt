package com.emma.emmaandroidexample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.emma.emmaandroidexample.emma.PushActivity
import com.emma.emmaandroidexample.ui.home.HomeViewModel
import com.emma.emmaandroidexample.ui.nativeAd.NativeAdViewModel
import com.emma.emmaandroidexample.ui.navigation.EmmaAndroidExampleNavigation
import com.emma.emmaandroidexample.ui.navigation.Routes
import com.emma.emmaandroidexample.ui.theme.EmmaAndroidExampleTheme
import com.emma.emmaandroidexample.ui.theme.EmmaDark
import io.emma.android.EMMA
import io.emma.android.interfaces.EMMADeviceIdListener
import io.emma.android.interfaces.EMMANotificationInterface
import io.emma.android.interfaces.EMMASessionStartListener
import io.emma.android.interfaces.EMMAUserInfoInterface
import io.emma.android.model.EMMAPushCampaign
import io.emma.android.model.EMMAPushOptions
import org.json.JSONObject

class MainActivity : ComponentActivity(), EMMANotificationInterface, EMMAUserInfoInterface, EMMADeviceIdListener {
    // VIEW MODELS
    private val mainViewModel: MainViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }
    //private val nativeAdViewModel: NativeAdViewModel by viewModels()

    // PROPERTIES
    private var sessionStarted: Boolean = false

    // LIFECYCLE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            !mainViewModel.isReady.value
        }

        // INICIALIZACIÓN DE EMMA
        try {
            val configuration = EMMA.Configuration.Builder(this)
                .setSessionKey("emmamobileM6wQcLX8S")
                .setDebugActive(BuildConfig.DEBUG)
                .build()

            EMMA.getInstance().startSession(
                configuration,
                EMMASessionStartListener { EMMA.getInstance().setCurrentActivity(this) }
            )

            Log.d("MainActivity", "EMMA initialized")
            mainViewModel.emmaIsInitialized()
            sessionStarted = true
        } catch (e: Exception) {
            mainViewModel.emmaIsNotInitialized()

            Log.d("MainActivity", "EMMA NOT initialized")
        }


        // SOLICTAR PERMISO AL USAURIO PARA RECIBIR NOTIFICACIONES
        EMMA.getInstance().requestNotificationPermission()

        // INICIALIZACIÓN DEL SISTEMA DE PUSH
        val pushOpt = EMMAPushOptions.Builder(PushActivity::class.java, R.drawable.ic_launcher_foreground)
            .setNotificationColor(EmmaDark.value.toInt())
            .setNotificationChannelName("My custom channel")
            .build()

        EMMA.getInstance().startPushSystem(pushOpt)

        EMMA.getInstance().checkForRichPushUrl() // Para comprobar el richPushUrl cuando se abre la app desde la notificación

        // Recuperar el identificador interno de EMMA
        EMMA.getInstance().getUserID()

        // Recuperar el perfil del usuario desde la aplicación
        EMMA.getInstance().getUserInfo()

        // Obtener el identificador del dispositivo (tipo UUID V4)
        EMMA.getInstance().getDeviceId(this)

        setContent {
            EmmaAndroidExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    //val deeplinkUrl = intent?.getStringExtra("DEEPLINK_URL")

                    //LaunchedEffect(deeplinkUrl) {
                    //    navController.navigate(Routes.HomeScreen.createRouteWithDeeplink(deeplinkUrl ?: "Deeplink did not arrive"))
                    //}

                    EmmaAndroidExampleNavigation(navController, homeViewModel, sessionStarted)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("MainActivity", "onNewIntent")
        EMMA.getInstance().onNewNotification(intent, true) // Verificará si el usuario ha recibido una notificación cuando la app está abierta
    }

    // Controlar qué se recibe de un Push
    override fun onPushOpen(pushCampaign: EMMAPushCampaign) {
        Log.d("MainActivity", pushCampaign.message)
    }

    override fun OnGetUserInfo(userInfo: JSONObject?) {
        userInfo?.let {
            Log.d("MainActivity", "UserInfo: $userInfo")
        }
    }

    override fun OnGetUserID(userId: Int) {
        Log.d("MainActivity", "UserId: $userId")
    }

    override fun onObtained(deviceId: String?) {
        Log.d("MainActivity", "DeviceId: $deviceId")
    }
}
