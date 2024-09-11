package com.emma.emmaandroidexample.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emma.emmaandroidexample.R
import com.emma.emmaandroidexample.ui.theme.EmmaDark
import com.emma.emmaandroidexample.ui.theme.EmmaLight

@Composable
fun EmmaButton(
    active: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(44.dp)
            .fillMaxWidth()
            .background(if (active) EmmaDark else EmmaLight)
            .clickable(enabled = active) { onClick() }
    ) {
        Text(
            text.uppercase(),
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun EmmaButton_Preview() {
    Column {
        EmmaButton(
            active = true,
            text = stringResource(id = R.string.session_button_start_session),
            onClick = { }
        )
        EmmaButton(
            active = false,
            text = stringResource(id = R.string.register_button_register_user),
            onClick = { }
        )
    }
}