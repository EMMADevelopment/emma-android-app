package com.emma.emmaandroidexample.ui.nativeAd

import android.util.Log
import androidx.lifecycle.ViewModel
import io.emma.android.EMMA
import io.emma.android.enums.CommunicationTypes
import io.emma.android.interfaces.EMMABatchNativeAdInterface
import io.emma.android.interfaces.EMMAInAppMessageInterface
import io.emma.android.interfaces.EMMANativeAdInterface
import io.emma.android.model.EMMACampaign
import io.emma.android.model.EMMANativeAd
import io.emma.android.model.EMMANativeAdRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NativeAdViewModel : ViewModel(), EMMAInAppMessageInterface, EMMABatchNativeAdInterface, EMMANativeAdInterface {
    // VIEW STATE
    private val _viewState = MutableStateFlow<NativeAdViewState>(NativeAdViewState.Idle)
    val viewState: StateFlow<NativeAdViewState> = _viewState.asStateFlow()

    // PROPERTIES
    private val _nativeAdReceived = MutableStateFlow<EMMANativeAd?>(null)
    val nativeAdReceived: StateFlow<EMMANativeAd?> = _nativeAdReceived.asStateFlow()

    private val _nativeAdsReceived = MutableStateFlow<List<EMMANativeAd>>(emptyList())
    val nativeAdsReceived: StateFlow<List<EMMANativeAd>> = _nativeAdsReceived.asStateFlow()

    // INIT
    init {
        Log.d("NativeAdViewModel", "Init NativeAdViewModel")
        _viewState.update { NativeAdViewState.Loading }
        Log.d("NativeAdViewModel", "NativeAdViewState.Loading")
        getNativeAd("template2") // Al ViewModel se le podría pasar el templateId dependiendo de la pantalla de donde se venga
        Log.d("NativeAdViewModel", "getNativeAd called")
    }

    // FUNCTIONS
    private fun getNativeAd(templateId: String) {
        val nativeAdRequest = EMMANativeAdRequest()
        nativeAdRequest.templateId = templateId
        EMMA.getInstance().getInAppMessage(nativeAdRequest, this)
    }

    fun getNativeAdBatch(templateId: String) {
        val nativeAdRequest = EMMANativeAdRequest()
        nativeAdRequest.templateId = templateId
        nativeAdRequest.isBatch = true
        EMMA.getInstance().getInAppMessage(nativeAdRequest, this)
    }

    fun openNativeAd(nativeAd: EMMANativeAd) {
        EMMA.getInstance().openNativeAd(nativeAd)
    }

    fun sendNativeAdClick(nativeAd: EMMANativeAd) {
        EMMA.getInstance().sendInAppClick(CommunicationTypes.NATIVE_AD, nativeAd)
    }

    override fun onReceived(nativeAd: EMMANativeAd?) {
        if (nativeAd != null) {
            val content = nativeAd.nativeAdContent
            val title = content?.get("Title")?.fieldValue
            if (title != null) {
                Log.d("NativeAdViewModel", "Received NativeAd with Title: $title")
                EMMA.getInstance().sendInAppImpression(CommunicationTypes.NATIVE_AD, nativeAd)
            }
            _nativeAdReceived.update { nativeAd }
            _viewState.update { NativeAdViewState.Loaded }
        } else {
            _viewState.update { NativeAdViewState.WithoutNativeAd }
        }
    }

    override fun onBatchReceived(nativeAds: MutableList<EMMANativeAd>?) {
        nativeAds?.forEach { nativeAd ->
            nativeAd.tag?.let { tag ->
                Log.d("NativeAdViewModel", "Received batch nativead with tag: $tag")
            }
        }
    }

    override fun onShown(campaign: EMMACampaign?) {
        Log.d("NativeAdViewModel", "Método onShown invocado")
    }

    override fun onHide(campaign: EMMACampaign?) {
        Log.d("NativeAdViewModel", "Método onHide invocado")
    }

    override fun onClose(campaign: EMMACampaign?) {
        Log.d("NativeAdViewModel", "Método onClose invocado")
    }
}