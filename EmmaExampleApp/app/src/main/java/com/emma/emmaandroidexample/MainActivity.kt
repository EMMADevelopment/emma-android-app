package com.emma.emmaandroidexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.emma.emmaandroidexample.ui.home.HomeScreen
import com.emma.emmaandroidexample.ui.navigation.EmmaAndroidExampleNavigation
import com.emma.emmaandroidexample.ui.navigation.Routes
import com.emma.emmaandroidexample.ui.theme.EmmaAndroidExampleTheme
import io.emma.android.EMMA

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val configuration = EMMA.Configuration.Builder(this)
            .setSessionKey("emmamobileM6wQcLX8S")
            .setDebugActive(BuildConfig.DEBUG)
            .build()

        EMMA.getInstance().startSession(configuration)

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
}

// Key: emmamobileM6wQcLX8S
