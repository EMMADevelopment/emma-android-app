package com.emma.emmaandroidexample.ui.home

import android.util.ArrayMap
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emma.emmaandroidexample.R
import com.emma.emmaandroidexample.data.MainItemData
import com.emma.emmaandroidexample.data.learnMoreData
import com.emma.emmaandroidexample.data.mainData
import com.emma.emmaandroidexample.ui.home.components.Header
import com.emma.emmaandroidexample.ui.home.components.LearnMoreItem
import com.emma.emmaandroidexample.ui.home.components.MainItem
import com.emma.emmaandroidexample.ui.navigation.Routes

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    deeplink: String?
) {
    LazyColumn {
        // HEADER
        item {
            Header()
        }
        // DEEPLINK
        if (deeplink != null) {
            item {
                MainItem(title = "Deeplink", description = deeplink, statusInfo = "Deeplink displayed", buttons = null, onClick = {})
            }
        } else {
            item {
                MainItem(
                    title = stringResource(R.string.deeplink_title),
                    description = stringResource(R.string.deeplink_description),
                    statusInfo = stringResource(R.string.deeplink_status),
                    buttons = null,
                    onClick = {}
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
                manageButtonClick(stringId, navController, homeViewModel)
            }
        }
        // FOOTER
        item {
            Footer()
        }
    }
}

fun manageButtonClick(stringId: Int, navController: NavHostController, homeViewModel: HomeViewModel) {
    when (stringId) {
        R.string.session_button_start_session -> Log.d("SALVA", "START SESSION")
        R.string.register_button_register_user -> {
            Log.d("SALVA", "REGISTER USER")
            homeViewModel.register("654321", "testSalva@emma.io")
        }
        R.string.log_button_log_in_user -> {
            Log.d("SALVA", "LOG IN USER")
            homeViewModel.login("654321", "testSalva@emma.io")
        }
        R.string.events_button_track_event -> {
            Log.d("SALVA", "TRACK EVENT")
            val attributes = mapOf<String, Any>("AttributeTest" to "EventTestSalva")
            homeViewModel.trackEvent("a6638efafbe61f63ee3fe31d7db476d4", attributes)
        }
        R.string.events_button_add_user -> {
            Log.d("SALVA", "ADD USER TAG 'TAG'")
            val tags = ArrayMap<String, String>()
            tags["TAG"] = "TEST SALVA"
            homeViewModel.addUserTag(tags)
        }
        R.string.communication_button_show_adball -> Log.d("SALVA", "SHOW ADBALL")
        R.string.communication_button_show_startview -> Log.d("SALVA", "SHOW STARTVIEW")
        R.string.communication_button_show_strip -> Log.d("SALVA", "SHOW STRIP")
        R.string.communication_button_show_native_ad -> {
            Log.d("SALVA", "SHOW NATIVE AD -> NAVIGATING...")
            navController.navigate(Routes.NativeAdScreen.route)
        }
        R.string.orders_button_start_order -> {
            Log.d("SALVA", "START ORDER")
            homeViewModel.startOrder("<ORDER_ID_SALVA_TEST>", "<CUSTOMER_ID_SALVA_TEST", 13.03f)
        }
        R.string.orders_button_add_order -> {
            Log.d("SALVA", "ADD PRODUCT")
            homeViewModel.addProduct("<PRODUCT_ID_SALVA_TEST>", "<PRODUCT_NAME_SALVA_TEST>", 1.0f, 13.03f)
        }
        R.string.orders_button_track_order -> {
            Log.d("SALVA", "TRACK ORDER")
            homeViewModel.trackOrder()
        }
        R.string.orders_button_cancel_order -> {
            Log.d("SALVA", "CANCEL ORDER")
            homeViewModel.cancelTransaction("<ORDER_ID_SALVA_TEST>")
        }
        R.string.idfa_button_request_idfa_tracking -> Log.d("SALVA", "REQUEST IDFA TRACKING")
        else -> {
            Log.d("SALVA", "Button not found!")
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
    HomeScreen(navController, homeViewModel, "https/...")
}