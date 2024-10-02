package com.emma.emmaandroidexample.ui.nativeAd

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import com.emma.emmaandroidexample.ui.nativeAd.components.NativeAdCard
import com.emma.emmaandroidexample.ui.theme.EmmaDark
import com.emma.emmaandroidexample.ui.theme.EmmaLight
import io.emma.android.EMMA
import io.emma.android.enums.CommunicationTypes
import io.emma.android.model.EMMANativeAd

@Composable
fun NativeAdScreen(
    nativeAdViewModel: NativeAdViewModel = viewModel()
) {
    val viewState by nativeAdViewModel.viewState.collectAsState()
    val nativeAd by nativeAdViewModel.nativeAdReceived.collectAsState()

    when (viewState) {
        is NativeAdViewState.Idle -> {}
        is NativeAdViewState.Loading -> { Loading() }
        is NativeAdViewState.Loaded -> {
            nativeAd?.let { Loaded(nativeAdViewModel, it) }
        }
        is NativeAdViewState.WithoutNativeAd -> { WithoutNativeAd() }
    }
}

@Composable
fun Loaded(nativeAdViewModel: NativeAdViewModel, nativeAd: EMMANativeAd) {
    val content = nativeAd.nativeAdContent
    val title = content?.get("Title")?.fieldValue
    val subtitle = content?.get("Subtitle")?.fieldValue
    val image = content["Main picture"]?.fieldValue
    val cta = content["CTA"]?.fieldValue

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(16.dp)
    ) {
        NativeAdCard(
            title ?: "Title",
            subtitle ?: "Subtitle 1",
            image ?: "https://picsum.photos/200/300",
        ) {
            nativeAdViewModel.openNativeAd(nativeAd)
        }
    }
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EmmaLight), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = EmmaDark, modifier = Modifier)
    }
}

@Composable
fun WithoutNativeAd() {
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
                    "Ups... Ha ocurrido un error. No hay NativeAd en activo en la plataforma de EMMA o no se ha recibido el NativeAd configurado.",
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

@Preview(showSystemUi = true)
@Composable
fun NativeAdScreen_Preview() {
    val nativeAdViewModel = NativeAdViewModel()
    NativeAdScreen(nativeAdViewModel)
}