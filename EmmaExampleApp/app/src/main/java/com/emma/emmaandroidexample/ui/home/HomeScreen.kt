package com.emma.emmaandroidexample.ui.home

import android.content.Context
import android.content.Intent
import android.util.ArrayMap
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emma.emmaandroidexample.R
import com.emma.emmaandroidexample.data.MainItemData
import com.emma.emmaandroidexample.data.learnMoreData
import com.emma.emmaandroidexample.data.mainData
import com.emma.emmaandroidexample.domain.Button
import com.emma.emmaandroidexample.emma.NativeAdsActivity
import com.emma.emmaandroidexample.ui.home.components.EmmaButton
import com.emma.emmaandroidexample.ui.home.components.Header
import com.emma.emmaandroidexample.ui.home.components.LearnMoreItem
import com.emma.emmaandroidexample.ui.home.components.MainItem
import com.emma.emmaandroidexample.ui.navigation.Routes

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    sessionStarted: Boolean,
    deeplink: String?
) {
    val context = LocalContext.current

    LazyColumn {
        // HEADER
        item {
            Header()
        }
        // DEEPLINK
        if (deeplink != null) {
            item {
                MainItem(
                    title = stringResource(R.string.deeplink_title),
                    description = deeplink,
                    statusInfo = stringResource(R.string.deeplink_status_displayed),
                    buttons = null,
                    onClick = {}
                )
            }
        } else {
            item {
                MainItem(
                    title = stringResource(R.string.deeplink_title),
                    description = stringResource(R.string.deeplink_description),
                    statusInfo = stringResource(R.string.deeplink_status_no),
                    buttons = null,
                    onClick = {}
                )
            }
        }
        // SESSION
        if (sessionStarted) {
            item {
                MainItem(
                    title = stringResource(R.string.session_title),
                    description = stringResource(R.string.session_description),
                    statusInfo = stringResource(R.string.session_status_started),
                    buttons = listOf(Button(title = R.string.session_button_start_session, active = false)),
                    onClick = {}
                )
            }
        } else {
            item {
                MainItem(
                    title = stringResource(R.string.session_title),
                    description = stringResource(R.string.session_description),
                    statusInfo =stringResource(R.string.session_status_no_started),
                    buttons = listOf(Button(title = R.string.session_button_start_session, active = true)),
                    onClick = {
                        Log.d("HomeScreen", "Click on START SESSION when EMMA is not initialized")
                    }
                )
            }
        }
        // THE OTHERS SECTIONS
        items(mainData) {
            MainItem(
                title = stringResource(id = it.title),
                description = it.description?.let { it1 -> stringResource(id = it1) },
                statusInfo = it.statusInfo?.let { it1 -> stringResource(id = it1) },
                buttons = it.buttons
            ) { stringId ->
                manageButtonClick(stringId, navController, homeViewModel, context)
            }
        }
        // FOOTER
        item {
            Footer()
        }
    }
}

fun manageButtonClick(
    stringId: Int,
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    context: Context
) {
    when (stringId) {
        R.string.session_button_start_session -> Log.d("SALVA", "START SESSION")
        R.string.register_button_register_user -> {
            Log.d("HomeScreen", "REGISTER USER")
            homeViewModel.register("654321", "testSalva@emma.io")
        }
        R.string.log_button_log_in_user -> {
            Log.d("HomeScreen", "LOG IN USER")
            homeViewModel.login("654321", "testSalva@emma.io")
        }
        R.string.events_button_track_event -> {
            Log.d("HomeScreen", "TRACK EVENT")
            val attributes = mapOf<String, Any>("AttributeTest" to "EventTestSalva")
            homeViewModel.trackEvent("a6638efafbe61f63ee3fe31d7db476d4", attributes)
        }
        R.string.events_button_add_user -> {
            Log.d("HomeScreen", "ADD USER TAG 'TAG'")
            val tags = ArrayMap<String, String>()
            tags["TAG"] = "TEST SALVA"
            homeViewModel.addUserTag(tags)
        }
        R.string.communication_button_show_adball -> {
            Log.d("HomeScreen", "SHOW ADBALL")
            homeViewModel.getAdBall()
        }
        R.string.communication_button_show_startview -> {
            Log.d("HomeScreen", "SHOW STARTVIEW")
            homeViewModel.getStartView()
        }
        R.string.communication_button_show_strip -> {
            Log.d("HomeScreen", "SHOW STRIP")
            homeViewModel.getStrip()
        }
        R.string.communication_button_show_native_ad -> {
            Log.d("HomeScreen", "SHOW NATIVE AD -> NAVIGATING...")
            navController.navigate(Routes.NativeAdScreen.route)
            //val intent = Intent(context, NativeAdsActivity::class.java)
            //context.startActivity(intent)
        }
        R.string.orders_button_start_order -> {
            Log.d("HomeScreen", "START ORDER")
            homeViewModel.startOrder("<ORDER_ID_SALVA_TEST>", "<CUSTOMER_ID_SALVA_TEST", 13.03f)
        }
        R.string.orders_button_add_order -> {
            Log.d("HomeScreen", "ADD PRODUCT")
            homeViewModel.addProduct("<PRODUCT_ID_SALVA_TEST>", "<PRODUCT_NAME_SALVA_TEST>", 1.0f, 13.03f)
        }
        R.string.orders_button_track_order -> {
            Log.d("HomeScreen", "TRACK ORDER")
            homeViewModel.trackOrder()
        }
        R.string.orders_button_cancel_order -> {
            Log.d("HomeScreen", "CANCEL ORDER")
            homeViewModel.cancelTransaction("<ORDER_ID_SALVA_TEST>")
        }
        R.string.idfa_button_request_idfa_tracking -> Log.d("SALVA", "REQUEST IDFA TRACKING")
        else -> {
            Log.d("HomeScreen", "Button not found!")
        }
    }
}

@Composable
fun Footer() {
    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 6.dp)
    ) {
        learnMoreData.forEach {
            LearnMoreItem(
                title = stringResource(id = it.title),
                description = stringResource(id = it.description),
                link = it.link
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TestMain_Preview() {
    val navController = rememberNavController()
    val homeViewModel = HomeViewModel()
    HomeScreen(navController, homeViewModel, true, "https/...")
}