package com.emma.emmaandroidexample.ui.navigation

sealed class Routes(val route: String) {
    object HomeScreen : Routes("homeScreen")
    object NativeAdScreen : Routes("nativeAdScreen")
}