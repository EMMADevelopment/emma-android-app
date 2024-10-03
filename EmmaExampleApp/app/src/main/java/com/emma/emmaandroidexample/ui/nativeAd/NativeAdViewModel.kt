package com.emma.emmaandroidexample.ui.nativeAd

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.emma.android.EMMA
import io.emma.android.enums.CommunicationTypes
import io.emma.android.interfaces.EMMABatchNativeAdInterface
import io.emma.android.interfaces.EMMAInAppMessageInterface
import io.emma.android.interfaces.EMMANativeAdInterface
import io.emma.android.model.EMMACampaign
import io.emma.android.model.EMMANativeAd
import io.emma.android.model.EMMANativeAdRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

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

        callForGettingNativeAds()
    }

    // FUNCTIONS
    private fun callForGettingNativeAds() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getNativeAd("template2") // Al ViewModel se le podría pasar el templateId dependiendo de la pantalla de donde se venga
                Log.d("NativeAdViewModel", "getNativeAd called")
            }
            withContext(Dispatchers.IO) {
                getNativeAdBatch("batch-template1")
                Log.d("NativeAdViewModel", "getNativeAdBatch called")
            }
            // Aquí gestionamos el estado de la vista dependiendo de los valores que tengamos en _nativeAdReceived
            // y _nativeAdsReceived. Esperamos con un breve delay para recibir la información de los NativeAds.
            // Se emplea así por exigencia situacional de esta app de prueba.
            delay(2000)
            withContext(Dispatchers.Main) {
                if (_nativeAdReceived.value == null && _nativeAdsReceived.value.isEmpty()) {
                    _viewState.update { NativeAdViewState.WithoutNativeAd }
                } else {
                    _viewState.update { NativeAdViewState.Loaded }
                }
            }
        }
    }

    private fun getNativeAd(templateId: String) {
        val nativeAdRequest = EMMANativeAdRequest()
        nativeAdRequest.templateId = templateId
        EMMA.getInstance().getInAppMessage(nativeAdRequest, this)
    }

    private fun getNativeAdBatch(templateId: String) {
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
        }
    }

    override fun onBatchReceived(nativeAds: MutableList<EMMANativeAd>?) {
        val nativeAdList: MutableList<EMMANativeAd> = mutableListOf()
        if (nativeAds != null) {
            nativeAds.forEach { nativeAd ->
                nativeAd.tag?.let { tag ->
                    Log.d("NativeAdViewModel", "Received batch nativead with tag: $tag")
                }
                val content = nativeAd.nativeAdContent
                val title = content?.get("Title")?.fieldValue
                Log.d("NativeAdViewModel", "Received batch nativead with title: $title")
                nativeAdList.add(nativeAd)
            }
            _nativeAdsReceived.update { nativeAdList }
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