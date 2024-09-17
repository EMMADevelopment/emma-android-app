package com.emma.emmaandroidexample.ui.home

import android.util.ArrayMap
import androidx.lifecycle.ViewModel
import io.emma.android.EMMA
import io.emma.android.model.EMMAEventRequest

class HomeViewModel: ViewModel() {
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
}