package com.example.downloader.data.ui

import android.view.Gravity
import com.google.android.material.listitem.SwipeableListItem

data class UIDevice(
    val isActive: Boolean,
    val codename: String,
    val codenameAlt: String,
    val lastUpdated: Int,
    val maintainerName: String,
    val model: String,
    val vendor: String,
    val version: Int,
    var isRevealed: Boolean = false,
    var swipeState: Int = SwipeableListItem.STATE_CLOSED,
    var swipeGravity: Int = Gravity.END
)
