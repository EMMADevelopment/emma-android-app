package com.emma.emmaandroidexample.ui.nativeAd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emma.emmaandroidexample.ui.theme.EmmaDark

@Composable
fun NativeAdScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(EmmaDark)
    ) {
        Text(
            "NATIVE AD",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Text(
                "Powered by EMMA",
                modifier = Modifier
                    .padding(bottom = 32.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun NativeAdScreen_Preview() {
    NativeAdScreen()
}