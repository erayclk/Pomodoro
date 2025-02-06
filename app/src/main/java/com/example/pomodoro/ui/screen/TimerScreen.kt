package com.example.pomodoro.ui.screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pomodoro.ui.viewmodel.PomodoroViewModel
import com.example.pomodoro.ui.viewmodel.PomodoroViewModel.TimerState

@Composable
fun TimerScreen(
    viewModel: PomodoroViewModel = hiltViewModel()
) {
    val timerState by viewModel.timerState.collectAsState()
    val remainingTime by viewModel.remainingTime.collectAsState()
    val points by viewModel.points.collectAsState()
    val streak by viewModel.streak.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Stats Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard(
                title = "Points",
                value = points.toString(),
                icon = Icons.Default.Star
            )
            StatCard(
                title = "Streak",
                value = streak.toString(),
                icon = Icons.Default.Whatshot
            )
        }

        // Timer Section
        Box(
            modifier = Modifier
                .size(300.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = remainingTime.toFloat() / (viewModel.settings.value.focusDuration * 60f),
                modifier = Modifier.fillMaxSize(),
                strokeWidth = 8.dp,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = formatTime(remainingTime),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }

        // Control Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (timerState) {
                TimerState.Idle -> {
                    FloatingActionButton(
                        onClick = { viewModel.startSession() },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Start")
                    }
                }
                TimerState.Running -> {
                    FloatingActionButton(
                        onClick = { viewModel.pauseSession() },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Default.Pause, contentDescription = "Pause")
                    }
                }
                TimerState.Paused -> {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        FloatingActionButton(
                            onClick = { viewModel.resumeSession() },
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Resume")
                        }
                        FloatingActionButton(
                            onClick = { viewModel.completeSession() },
                            containerColor = MaterialTheme.colorScheme.error
                        ) {
                            Icon(Icons.Default.Stop, contentDescription = "Stop")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}

private fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
} 