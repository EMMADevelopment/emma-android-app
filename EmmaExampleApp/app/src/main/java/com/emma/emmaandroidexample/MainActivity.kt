package com.emma.emmaandroidexample

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
import io.emma.android.interfaces.EMMAUserInfoInterface
import io.emma.android.model.EMMAPushOptions
import org.json.JSONObject

class MainActivity : ComponentActivity(), EMMAUserInfoInterface, EMMADeviceIdListener {
    // VIEW MODELS
    private val homeViewModel: HomeViewModel by viewModels()
    private val nativeAdViewModel: NativeAdViewModel by viewModels()

    // LIFECYCLE
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
            .setNotificationColor(EmmaDark.value.toInt())
            .setNotificationChannelName("My custom channel")
            .build()

        EMMA.getInstance().startPushSystem(pushOpt)

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

                    val deeplinkUrl = intent?.getStringExtra("DEEPLINK_URL")

                    LaunchedEffect(deeplinkUrl) {
                        navController.navigate(Routes.HomeScreen.createRouteWithDeeplink(deeplinkUrl ?: "Deeplink did not arrive"))
                    }

                    EmmaAndroidExampleNavigation(navController, homeViewModel, nativeAdViewModel)
                }
            }
        }
    }

    override fun OnGetUserInfo(userInfo: JSONObject?) {
        userInfo?.let {
            Log.d("MainActivity", "UserInfo: $userInfo")
        }
    }

    override fun OnGetUserID(userId: Int) {
        Log.d("MainActivity", "UserId: $userId.toString()")
    }

    override fun onObtained(deviceId: String?) {
        Log.d("MainActivity", "DeviceId: $deviceId.toString()")
    }
}
