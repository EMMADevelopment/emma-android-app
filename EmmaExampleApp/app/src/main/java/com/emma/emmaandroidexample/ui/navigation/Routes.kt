package com.emma.emmaandroidexample.ui.navigation

sealed class Routes(val route: String) {
    object HomeScreen : Routes("homeScreen/{deeplink}") {
        fun createRoute() = "homeScreen"
        fun createRouteWithDeeplink(deeplink: String) = "homeScreen/$deeplink"
    }
    object NativeAdScreen : Routes("nativeAdScreen")
    object DeeplinkScreen : Routes("deeplink")
}