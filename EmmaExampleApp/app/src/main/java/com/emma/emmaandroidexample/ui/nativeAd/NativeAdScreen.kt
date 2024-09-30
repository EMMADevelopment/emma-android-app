package com.emma.emmaandroidexample.ui.nativeAd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.emma.emmaandroidexample.ui.theme.EmmaDark

@Composable
fun NativeAdScreen(
    nativeAdViewModel: NativeAdViewModel = viewModel()
) {
    val nativeAd by nativeAdViewModel.nativeAdReceived.collectAsState()

    if (nativeAd != null) {
        nativeAdViewModel.openNativeAd(nativeAd!!)
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(EmmaDark)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(0.25f)
                        .fillMaxWidth()
                ) {
                    Text(
                        "NATIVE AD",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        "Ups... Ha ocurrido un error. No se ha recibido el NativeAd.",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(0.25f)
                        .fillMaxWidth()
                ) {
                    Text("Powered by EMMA")
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun NativeAdScreen_Preview() {
    val nativeAdViewModel = NativeAdViewModel()
    NativeAdScreen(nativeAdViewModel)
}