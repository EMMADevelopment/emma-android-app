package com.emma.emmaandroidexample.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emma.emmaandroidexample.R
import com.emma.emmaandroidexample.domain.Button

@Composable
fun MainItem(
    title: String,
    description: String?,
    statusInfo: String?,
    buttons: List<Button>?,
    onClick: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            if (statusInfo != null) {
                Text(
                    text = statusInfo,
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
            }
        }
        if (description != null) {
            Text(
                text = description,
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        }
        if (buttons != null) {
            ButtonsGrid(
                buttons = buttons,
                onClick = { stringId -> onClick(stringId) },
                modifier = Modifier
                    .padding(top = 4.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainItem_Preview() {
    LazyColumn {
        item {
            MainItem(
                title = "Deeplink",
                description = "Received deeplink will be displayed here.",
                statusInfo = "No deeplink",
                buttons = null,
                onClick = { }
            )
        }
        item {
            MainItem(
                title = "Session",
                description = "Session is required. Usually, it should be triggered when the App is ready.",
                statusInfo = "Session started",
                buttons = listOf(
                    Button(title = R.string.session_button_start_session, active = false)
                ),
                onClick = { }
            )
        }
        item {
            MainItem(
                title = "Register User",
                description = null,
                statusInfo = null,
                buttons = listOf(
                    Button(title = R.string.register_button_register_user, active = true)
                ),
                onClick = { }
            )
        }
        item {
            MainItem(
                title = "Events and Extras",
                description = "These buttons do not have UI feedback",
                statusInfo = null,
                buttons = listOf(
                    Button(title = R.string.events_button_track_event, active = true),
                    Button(title = R.string.events_button_add_user, active = true),
                ),
                onClick = { }
            )
        }
        item {
            MainItem(
                title = "In-App Communication",
                description = "Try our in-app communications:",
                statusInfo = null,
                buttons = listOf(
                    Button(title = R.string.communication_button_show_adball, active = true),
                    Button(title = R.string.communication_button_show_startview, active = true),
                    Button(title = R.string.communication_button_show_strip, active = true),
                    Button(title = R.string.communication_button_show_native_ad, active = true),
                ),
                onClick = { }
            )
        }
    }
}