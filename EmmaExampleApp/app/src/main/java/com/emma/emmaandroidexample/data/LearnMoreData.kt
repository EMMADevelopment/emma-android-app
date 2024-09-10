package com.emma.emmaandroidexample.data

import com.emma.emmaandroidexample.R

data class LearnMoreData(
    val title: Int,
    val description: Int,
    val link: String,
)

val learnMoreData: List<LearnMoreData> = listOf(
    LearnMoreData(
        title = R.string.emma_sdk_title,
        description = R.string.emma_sdk_description,
        link = "https://developer.emma.io/es/home"
    ),
    LearnMoreData(
        title = R.string.ios_title,
        description = R.string.ios_description,
        link = "https://github.com/EMMADevelopment/eMMa-iOS-SDK"
    ),
    LearnMoreData(
        title = R.string.android_title,
        description = R.string.android_description,
        link = "https://github.com/EMMADevelopment/emma-android-sdk"
    ),
    LearnMoreData(
        title = R.string.cordova_title,
        description = R.string.cordova_description,
        link = "https://github.com/EMMADevelopment/Cordova-Plugin-EMMA-SDK"
    ),
    LearnMoreData(
        title = R.string.ionic_title,
        description = R.string.ionic_description,
        link = "https://github.com/EMMADevelopment/EMMAIonicExample"
    ),
    LearnMoreData(
        title = R.string.flutter_title,
        description = R.string.flutter_description,
        link = "https://github.com/EMMADevelopment/emma_flutter_sdk"
    ),
    LearnMoreData(
        title = R.string.xamarin_title,
        description = R.string.xamarin_description,
        link = "https://github.com/EMMADevelopment/EMMA-Xamarin-SDK"
    ),
)