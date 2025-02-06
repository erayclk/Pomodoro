package com.example.pomodoro.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pomodoro.R
import com.example.pomodoro.data.model.UserSettings
import com.example.pomodoro.ui.viewmodel.PomodoroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: PomodoroViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()
    var tempSettings by remember { mutableStateOf(settings) }

    LaunchedEffect(settings) {
        tempSettings = settings
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Focus Duration Slider
        Text(
            text = stringResource(R.string.focus_duration),
            style = MaterialTheme.typography.titleMedium
        )
        Slider(
            value = tempSettings.focusDuration.toFloat(),
            onValueChange = { tempSettings = tempSettings.copy(focusDuration = it.toInt()) },
            valueRange = 1f..60f,
            steps = 59
        )
        Text(text = stringResource(R.string.duration_format, tempSettings.focusDuration))

        Spacer(modifier = Modifier.height(16.dp))

        // Short Break Duration Slider
        Text(
            text = stringResource(R.string.short_break_duration),
            style = MaterialTheme.typography.titleMedium
        )
        Slider(
            value = tempSettings.shortBreakDuration.toFloat(),
            onValueChange = { tempSettings = tempSettings.copy(shortBreakDuration = it.toInt()) },
            valueRange = 1f..15f,
            steps = 14
        )
        Text(text = stringResource(R.string.duration_format, tempSettings.shortBreakDuration))

        Spacer(modifier = Modifier.height(16.dp))

        // Long Break Duration Slider
        Text(
            text = stringResource(R.string.long_break_duration),
            style = MaterialTheme.typography.titleMedium
        )
        Slider(
            value = tempSettings.longBreakDuration.toFloat(),
            onValueChange = { tempSettings = tempSettings.copy(longBreakDuration = it.toInt()) },
            valueRange = 1f..30f,
            steps = 29
        )
        Text(text = stringResource(R.string.duration_format, tempSettings.longBreakDuration))

        Spacer(modifier = Modifier.height(16.dp))

        // Daily Target Sessions Slider
        Text(
            text = stringResource(R.string.daily_target_sessions),
            style = MaterialTheme.typography.titleMedium
        )
        Slider(
            value = tempSettings.targetSessionsPerDay.toFloat(),
            onValueChange = { tempSettings = tempSettings.copy(targetSessionsPerDay = it.toInt()) },
            valueRange = 1f..20f,
            steps = 19
        )
        Text(text = stringResource(R.string.sessions_format, tempSettings.targetSessionsPerDay))

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.updateSettings(tempSettings) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.save_settings))
        }
    }
} 