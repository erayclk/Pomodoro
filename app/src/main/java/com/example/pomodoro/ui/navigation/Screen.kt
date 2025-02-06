package com.example.pomodoro.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pomodoro.R

sealed class Screen(
    val route: String,
    val icon: ImageVector,
    val titleResId: Int
) {
    object Timer : Screen(
        route = "timer",
        icon = Icons.Default.Timer,
        titleResId = R.string.timer
    )
    
    object Stats : Screen(
        route = "stats",
        icon = Icons.Default.Whatshot,
        titleResId = R.string.stats
    )
    
    object Settings : Screen(
        route = "settings",
        icon = Icons.Default.Settings,
        titleResId = R.string.settings
    )
} 