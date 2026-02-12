package com.example.downloader.data.network

import com.google.gson.annotations.SerializedName

data class DeviceInfo(
    @SerializedName("active") val isActive: Boolean,
    @SerializedName("archive") val archive: String,
    @SerializedName("codename") val codename: String,
    @SerializedName("codename_alt") val codenameAlt: String,
    @SerializedName("download_link") val downloadLink: String,
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("maintainer") val maintainer: List<Maintainer>,
    @SerializedName("model") val model: String,
    @SerializedName("release") val release: String,
    @SerializedName("vendor") val vendor: String,
    @SerializedName("version") val version: String,
    @SerializedName("xda") val xda: String
)