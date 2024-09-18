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
    private val _nativeAdReceived = MutableStateFlow<EMMANativeAd?>(null)
    val nativeAdReceived: StateFlow<EMMANativeAd?> = _nativeAdReceived.asStateFlow()

    private val _nativeAdsReceived = MutableStateFlow<List<EMMANativeAd>>(emptyList())
    val nativeAdsReceived: StateFlow<List<EMMANativeAd>> = _nativeAdsReceived.asStateFlow()

    init {
        getNativeAd("template2")
    }

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
            val title = content?.get("title")?.fieldValue
            if (title != null) {
                Log.d("SALVA", "Received NativeAd with Title: $title")
                EMMA.getInstance().sendInAppImpression(CommunicationTypes.NATIVE_AD, nativeAd)
            }
            _nativeAdReceived.update { nativeAd }
        }
    }

    override fun onBatchReceived(nativeAds: MutableList<EMMANativeAd>?) {
        nativeAds?.forEach { nativeAd ->
            nativeAd.tag?.let { tag ->
                Log.d("SALVA", "Received batch nativead with tag: $tag")
            }
        }
    }

    override fun onShown(campaign: EMMACampaign?) {
        Log.d("SALVA", "Método onShown invocado")
    }

    override fun onHide(campaign: EMMACampaign?) {
        Log.d("SALVA", "Método onHide invocado")
    }

    override fun onClose(campaign: EMMACampaign?) {
        Log.d("SALVA", "Método onClose invocado")
    }
}