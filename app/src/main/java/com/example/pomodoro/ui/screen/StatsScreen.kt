package com.example.pomodoro.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pomodoro.R
import com.example.pomodoro.ui.viewmodel.PomodoroViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun StatsScreen(
    viewModel: PomodoroViewModel = hiltViewModel()
) {
    val points by viewModel.points.collectAsState()
    val streak by viewModel.streak.collectAsState()
    val settings by viewModel.settings.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        item {
            Text(
                text = stringResource(R.string.your_progress),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        // Achievement Cards
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AchievementCard(
                    title = stringResource(R.string.total_points),
                    value = points.toString(),
                    icon = Icons.Default.EmojiEvents,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                AchievementCard(
                    title = stringResource(R.string.best_streak),
                    value = streak.toString(),
                    icon = Icons.Default.Whatshot,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Daily Target Progress
        item {
            DailyTargetCard(
                completedSessions = points,
                targetSessions = settings.targetSessionsPerDay
            )
        }

        // Recent Activity
        item {
            Text(
                text = stringResource(R.string.recent_activity),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        // Activity Items
        items(5) { index ->
            ActivityItem(
                date = LocalDateTime.now().minus(index.toLong(), ChronoUnit.DAYS),
                sessionsCompleted = (5 - index),
                totalMinutes = (5 - index) * 25
            )
        }
    }
}

@Composable
private fun AchievementCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(4.dp),
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
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DailyTargetCard(
    completedSessions: Int,
    targetSessions: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.daily_target),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$completedSessions/$targetSessions",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = completedSessions.toFloat() / targetSessions,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
        }
    }
}

@Composable
private fun ActivityItem(
    date: LocalDateTime,
    sessionsCompleted: Int,
    totalMinutes: Int
) {
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
    val today = LocalDateTime.now()
    val displayText = when {
        date.toLocalDate().isEqual(today.toLocalDate()) -> stringResource(R.string.today)
        date.toLocalDate().isEqual(today.minusDays(1).toLocalDate()) -> stringResource(R.string.yesterday)
        else -> stringResource(R.string.days_ago, ChronoUnit.DAYS.between(date.toLocalDate(), today.toLocalDate()))
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = displayText,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.completed_sessions, sessionsCompleted),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Text(
                text = stringResource(R.string.duration_format, totalMinutes),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
} 