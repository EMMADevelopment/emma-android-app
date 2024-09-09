package com.emma.emmaandroidexample.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                        Button(active = item.active, text = item.title)
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
                Button(title = "Start session", active = false),
            )
        )
        ButtonsGrid(
            buttons = listOf(
                Button(title = "Start session", active = false),
                Button(title = "Register user", active = true),
            )
        )
        ButtonsGrid(
            buttons = listOf(
                Button(title = "Start order", active = false),
                Button(title = "Add product", active = true),
                Button(title = "Track order", active = true),
            )
        )
        ButtonsGrid(
            buttons = listOf(
                Button(title = "Show adball", active = true),
                Button(title = "Show startview", active = true),
                Button(title = "Show strip", active = true),
                Button(title = "Show native ad", active = true),
            )
        )
    }
}