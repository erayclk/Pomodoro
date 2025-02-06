package com.example.pomodoro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "pomodoro_sessions")
data class PomodoroSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startTime: Date,
    val endTime: Date? = null,
    val duration: Int, // in minutes
    val isCompleted: Boolean = false,
    val points: Int = 0,
    val streak: Int = 1 // consecutive completed sessions
) 