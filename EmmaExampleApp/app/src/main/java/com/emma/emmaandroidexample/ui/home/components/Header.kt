package com.emma.emmaandroidexample.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emma.emmaandroidexample.R
import com.emma.emmaandroidexample.ui.theme.EmmaDark

@Composable
fun Header() {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
            .background(EmmaDark)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo EMMA",
            alpha = 0.2f,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 30.dp)
                .height(120.dp)
                .width(250.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy((-5).dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "Welcome to",
                    color = Color.White,
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
                Text(
                    "EMMA",
                    color = Color.White,
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun Header_Preview() {
    Header()
}