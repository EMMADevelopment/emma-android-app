package com.emma.emmaandroidexample.ui.home

import androidx.lifecycle.ViewModel
import io.emma.android.EMMA

class HomeViewModel: ViewModel() {
    // Enviar información sobre los registros en la aplicación
    fun register(userId: String, mail: String) {
        EMMA.getInstance().registerUser(userId, mail)
    }


}