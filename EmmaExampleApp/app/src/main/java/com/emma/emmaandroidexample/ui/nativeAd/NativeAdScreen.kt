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
import androidx.compose.runtime.LaunchedEffect
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
import com.emma.emmaandroidexample.ui.nativeAd.components.NativeAdCarousel
import com.emma.emmaandroidexample.ui.theme.EmmaDark
import com.emma.emmaandroidexample.ui.theme.EmmaMedium
import io.emma.android.model.EMMANativeAd

@Composable
fun NativeAdScreen(
    nativeAdViewModel: NativeAdViewModel = viewModel()
) {
    val viewState by nativeAdViewModel.viewState.collectAsState()
    val nativeAd by nativeAdViewModel.nativeAdReceived.collectAsState()
    val nativeAdsBatch by nativeAdViewModel.nativeAdsReceived.collectAsState()

    // A ELIMINAR
    LaunchedEffect(nativeAdsBatch) {
        if (nativeAdsBatch.isNotEmpty()) {
            nativeAdsBatch.forEach { nativeAd ->
                val content = nativeAd.nativeAdContent
                val title = content?.get("Title")?.fieldValue
                Log.d("NativeAdScreen", title ?: "Title vacío")
            }
        }
    }

    when (viewState) {
        is NativeAdViewState.Idle -> {}
        is NativeAdViewState.Loading -> { Loading() }
        is NativeAdViewState.Loaded -> { Loaded(nativeAdViewModel, nativeAd, nativeAdsBatch) }
        is NativeAdViewState.WithoutNativeAd -> { WithoutNativeAd() }
    }
}

@Composable
fun Loaded(
    nativeAdViewModel: NativeAdViewModel,
    nativeAd: EMMANativeAd?,
    nativeAdsBatch: List<EMMANativeAd>
) {
    val content = nativeAd?.nativeAdContent
    val title = content?.get("Title")?.fieldValue
    val subtitle = content?.get("Subtitle")?.fieldValue
    val image = content?.get("Main picture")?.fieldValue
    val cta = content?.get("CTA")?.fieldValue

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(16.dp)
    ) {
        if (nativeAd != null) {
            NativeAdCard(
                title ?: "Title",
                subtitle ?: "Subtitle 1",
                image ?: "",
            ) {
                nativeAdViewModel.openNativeAd(nativeAd)
            }
        }
        NativeAdCarousel(nativeAds = nativeAdsBatch) { index ->
            nativeAdViewModel.openNativeAd(nativeAdsBatch[index])
        }
    }
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EmmaMedium), contentAlignment = Alignment.Center
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Ups... Ha ocurrido un error. No se ha recibido ningún NativeAd.",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "No hay NativeAd en activo en la plataforma de EMMA con la template asignada en el código o no se ha recibido el NativeAd único o múltiple configurado.",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }

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

@Preview(showSystemUi = true)
@Composable
fun Loading_Preview() {
    Loading()
}

@Preview(showSystemUi = true)
@Composable
fun WithoutNativeAd_Preview() {
    WithoutNativeAd()
}