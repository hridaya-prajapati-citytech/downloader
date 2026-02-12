package com.example.downloader.data.network

import com.google.gson.annotations.SerializedName

data class Maintainer(
    @SerializedName("display_name") val maintainerName: String,
    @SerializedName("github") val maintainerGithub: String,
    @SerializedName("telegram") val maintainerTelegram: String
)