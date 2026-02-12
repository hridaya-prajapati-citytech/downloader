package com.example.downloader.data.network

import com.google.gson.annotations.SerializedName

data class NetworkDevice(
    @SerializedName("active") val isActive: Boolean,
    @SerializedName("codename") val codename: String,
    @SerializedName("codename_alt") val codenameAlt: String,
    @SerializedName("last_updated") val lastUpdated: Int,
    @SerializedName("maintainer_name") val maintainerName: String,
    @SerializedName("model") val model: String,
    @SerializedName("vendor") val vendor: String,
    @SerializedName("version") val version: Int
)