package com.emma.emmaandroidexample.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emma.emmaandroidexample.ui.theme.EmmaDark

@Composable
fun LearnMoreItem(
    title: String,
    description: String,
    link: String,
) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(link)) }
    Column(
        modifier = Modifier
            .clickable { context.startActivity(intent) }
    ) {
        Divider(thickness = 1.dp, color = Color.LightGray)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .weight(0.5f)
            ) {
                Text(
                    text = title,
                    color = EmmaDark,
                    fontSize = 17.sp,
                )
            }
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = description,
                    color = Color.DarkGray,
                    fontSize = 17.sp,
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LearnMore_Preview() {
    Column {
        LearnMoreItem("EMMA SDK", "Documentation & Support", "")
        LearnMoreItem("iOS", "EMMA SDK for iOS", "")
        LearnMoreItem("Android", "EMMA SDK for Android", "")
    }
}