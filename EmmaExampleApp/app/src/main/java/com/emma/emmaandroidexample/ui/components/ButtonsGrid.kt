package com.emma.emmaandroidexample.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emma.emmaandroidexample.R
import com.emma.emmaandroidexample.domain.Button

@Composable
fun ButtonsGrid(
    buttons: List<Button>,
    modifier: Modifier = Modifier
) {
    val numberOfColumns = 2

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp) // Espacio entre filas
    ) {
        buttons.chunked(numberOfColumns).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp) // Espacio entre columnas
            ) {
                rowItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(active = item.active, text = stringResource(id = item.title))
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ButtonsGrid_Preview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ButtonsGrid(
            buttons = listOf(
                Button(title = R.string.session_button_start_session, active = false),
            )
        )
        ButtonsGrid(
            buttons = listOf(
                Button(title = R.string.session_button_start_session, active = false),
                Button(title = R.string.register_button_register_user, active = true),
            )
        )
        ButtonsGrid(
            buttons = listOf(
                Button(title = R.string.orders_button_start_order, active = false),
                Button(title = R.string.orders_button_add_order, active = true),
                Button(title = R.string.orders_button_track_order, active = true),
            )
        )
        ButtonsGrid(
            buttons = listOf(
                Button(title = R.string.communication_button_show_adball, active = true),
                Button(title = R.string.communication_button_show_startview, active = true),
                Button(title = R.string.communication_button_show_strip, active = true),
                Button(title = R.string.communication_button_show_native_ad, active = true),
            )
        )
    }
}