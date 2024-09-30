package com.emma.emmaandroidexample.data

import com.emma.emmaandroidexample.R
import com.emma.emmaandroidexample.domain.Button

data class MainItemData(
    val title: Int,
    val description: Int?,
    val statusInfo: Int?,
    val buttons: List<Button>?,
)

val mainData: List<MainItemData> = listOf(
    MainItemData(
        title = R.string.register_title,
        description = null,
        statusInfo = null,
        buttons = listOf(
            Button(title = R.string.register_button_register_user, active = true)
        )
    ),
    MainItemData(
        title = R.string.log_title,
        description = null,
        statusInfo = null,
        buttons = listOf(
            Button(title = R.string.log_button_log_in_user, active = true)
        )
    ),
    MainItemData(
        title = R.string.events_title,
        description = R.string.events_description,
        statusInfo = null,
        buttons = listOf(
            Button(title = R.string.events_button_track_event, active = true),
            Button(title = R.string.events_button_add_user, active = true),
        )
    ),
    MainItemData(
        title = R.string.communication_title,
        description = R.string.communication_description,
        statusInfo = null,
        buttons = listOf(
            Button(title = R.string.communication_button_show_adball, active = true),
            Button(title = R.string.communication_button_show_startview, active = true),
            Button(title = R.string.communication_button_show_strip, active = true),
            Button(title = R.string.communication_button_show_native_ad, active = true),
        )
    ),
    MainItemData(
        title = R.string.orders_title,
        description = R.string.orders_description,
        statusInfo = null,
        buttons = listOf(
            Button(title = R.string.orders_button_start_order, active = true),
            Button(title = R.string.orders_button_add_order, active = false),
            Button(title = R.string.orders_button_track_order, active = false),
            Button(title = R.string.orders_button_cancel_order, active = true),
        )
    ),
    MainItemData(
        title = R.string.idfa_title,
        description = R.string.idfa_description,
        statusInfo = null,
        buttons = listOf(
            Button(title = R.string.idfa_button_request_idfa_tracking, active = false)
        )
    ),
    MainItemData(
        title = R.string.learn_more_title,
        description = R.string.learn_more_description,
        statusInfo = null,
        buttons = null
    ),
)