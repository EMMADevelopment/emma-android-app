package com.emma.emmaandroidexample.ui.nativeAd

sealed class NativeAdViewState {
    data object Idle : NativeAdViewState()
    data object Loading : NativeAdViewState()
    data object Loaded : NativeAdViewState()
    data object WithoutNativeAd : NativeAdViewState()
}