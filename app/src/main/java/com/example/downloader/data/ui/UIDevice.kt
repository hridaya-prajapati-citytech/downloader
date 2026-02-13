package com.example.downloader.data.ui

data class UIDevice(
    val isActive: Boolean,
    val codename: String,
    val codenameAlt: String,
    val lastUpdated: Int,
    val maintainerName: String,
    val model: String,
    val vendor: String,
    val version: Int,
    var isRevealed: Boolean
)
