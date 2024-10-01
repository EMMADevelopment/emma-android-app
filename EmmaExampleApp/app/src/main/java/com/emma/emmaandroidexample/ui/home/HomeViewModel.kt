package com.emma.emmaandroidexample.ui.home

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.ArrayMap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.emma.android.EMMA
import io.emma.android.interfaces.EMMAInAppMessageInterface
import io.emma.android.model.EMMACampaign
import io.emma.android.model.EMMAEventRequest
import io.emma.android.model.EMMAInAppRequest
import io.emma.android.model.EMMANativeAd
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(application: Application): AndroidViewModel(application), EMMAInAppMessageInterface {

    private val _deeplink = MutableStateFlow<String?>(null)
    val deeplink: StateFlow<String?> = _deeplink.asStateFlow()

    private val deeplinkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val deeplink = intent?.getStringExtra("DEEPLINK")
            _deeplink.value = deeplink
        }
    }

    init {
        val filter = IntentFilter("UPDATE_DEEPLINK")
        LocalBroadcastManager.getInstance(getApplication()).registerReceiver(deeplinkReceiver, filter)
    }

    override fun onCleared() {
        super.onCleared()
        // Desregistra el receptor para evitar fugas de memoria
        LocalBroadcastManager.getInstance(getApplication()).unregisterReceiver(deeplinkReceiver)
    }

    // Enviar información sobre los registros en la aplicación
    fun register(userId: String, mail: String) {
        EMMA.getInstance().registerUser(userId, mail)
    }

    // Enviar información sobre los eventos login
    fun login(userId: String, mail: String) {
        EMMA.getInstance().loginUser(userId, mail)
    }

    // Iniciar la transacción
    fun startOrder(orderId: String, customerId: String, totalPrice: Float) {
        EMMA.getInstance().startOrder(orderId, customerId, totalPrice)
    }

    // Una vez iniciada la transacción hay que añadir los productos a la misma
    fun addProduct(productId: String, name: String, qty: Float, price: Float) {
        EMMA.getInstance().addProduct(productId, name, qty, price)
    }

    // Una vez tenemos todos los productos añadidos, ejecutamos la medición de la transacción
    fun trackOrder() {
        EMMA.getInstance().trackOrder()
    }

    // Cancelar el tracking de una transacción
    fun cancelTransaction(orderId: String) {
        EMMA.getInstance().cancelOrder(orderId)
    }

    // Enviar un evento propio de la aplicación
    fun trackEvent(token: String, attributes: Map<String, Any>?) {
        val eventRequest = EMMAEventRequest(token)
        attributes?.let {
            eventRequest.attributes = it
        }

        EMMA.getInstance().trackEvent(eventRequest)
    }

    // Enriquecer el perfil del usuario con información almacenada mediante Clave / Valor a la que le llamamos tags de usuario
    fun addUserTag(tags: ArrayMap<String, String>) {
        EMMA.getInstance().trackExtraUserInfo(tags)
    }

    // La StartView es un formato de comunicación que te permite mostrar un contenido HTML,
    // alojado en una URL, en un WebView a pantalla completa
    fun getStartView() {
        val startViewRequest = EMMAInAppRequest(EMMACampaign.Type.STARTVIEW)
        EMMA.getInstance().getInAppMessage(startViewRequest)
    }

    // El AdBall es una pequeña vista circular que muestra una imagen.
    // Esta vista se puede arrastrar por toda la pantalla y eliminar de ella en cualquier momento,
    // contiene un CTA que es una URL con contenido HTML que lanza un WebView al hacer clic en la ella
    fun getAdBall() {
        val adBallRequest = EMMAInAppRequest(EMMACampaign.Type.ADBALL)
        EMMA.getInstance().getInAppMessage(adBallRequest)
    }

    // El strip te permite mostrar un banner en lo alto de la pantalla del dispositivo con un texto que va pasando a modo de carrusel,
    // variables como el tiempo de duración de la rotación o el tiempo de visualización son configurables desde el Dashboard
    fun getStrip() {
        val stripRequest = EMMAInAppRequest(EMMACampaign.Type.STRIP)
        EMMA.getInstance().getInAppMessage(stripRequest)
    }

    // EMMAInAppMessageInterface
    override fun onShown(campaign: EMMACampaign?) {
        Log.d("HomeViewModel", "Método onShown invocado")
    }

    override fun onHide(campaign: EMMACampaign?) {
        Log.d("HomeViewModel", "Método onHide invocado")
    }

    override fun onClose(campaign: EMMACampaign?) {
        Log.d("HomeViewModel", "Método onClose invocado")
    }

    fun updateDeeplink(uri: String) {
        Log.d("HomeViewModel", "Se ha actualizado a: $uri")
        _deeplink.update { uri }
        Log.d("HomeViewModel", "Deeplink es: ${deeplink.value}")
    }
}