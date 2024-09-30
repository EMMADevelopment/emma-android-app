package com.emma.emmaandroidexample.emma

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import io.emma.android.EMMA
import io.emma.android.enums.CommunicationTypes
import io.emma.android.interfaces.EMMABatchNativeAdInterface
import io.emma.android.interfaces.EMMAInAppMessageInterface
import io.emma.android.interfaces.EMMANativeAdInterface
import io.emma.android.model.EMMACampaign
import io.emma.android.model.EMMANativeAd
import io.emma.android.model.EMMANativeAdRequest

class NativeAdsActivity: ComponentActivity(),
    EMMAInAppMessageInterface,
    EMMABatchNativeAdInterface,
    EMMANativeAdInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("NativeAdsActivity", "Apertura de NativeAdsActivity")
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

    override fun onBatchReceived(nativeAds: MutableList<EMMANativeAd>) {
        nativeAds.forEach { nativeAd ->
            nativeAd.tag?.let { tag ->
                Log.d("SALVA", "Received batch nativead with tag: $tag")
            }
        }
    }

    override fun onReceived(nativeAd: EMMANativeAd) {
        Log.d("SALVA", "Llega")
        val content = nativeAd.nativeAdContent
        Log.d("SALVA", content.toString())
        val title = content["Title"]?.fieldValue
        if (title !=  null) {
            Log.d("SALVA", "Received NativeAd with Title: $title")
            openNativeAd(nativeAd)
            EMMA.getInstance().sendInAppImpression(CommunicationTypes.NATIVE_AD, nativeAd)
        }
    }

    fun openNativeAd(nativeAd: EMMANativeAd) {
        EMMA.getInstance().openNativeAd(nativeAd)
    }

    fun sendNativeAdClick(nativeAd: EMMANativeAd) {
        EMMA.getInstance().sendInAppClick(CommunicationTypes.NATIVE_AD, nativeAd)
    }

    override fun onShown(campaign: EMMACampaign) {
        Log.d("SALVA", "Método onShown invocado")
    }

    override fun onHide(campaign: EMMACampaign) {
        Log.d("SALVA", "Método onHide invocado")
    }

    override fun onClose(campaign: EMMACampaign) {
        Log.d("SALVA", "Método onClose invocado")
    }
}