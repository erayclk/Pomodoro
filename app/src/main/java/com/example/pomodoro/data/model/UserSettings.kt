package com.example.pomodoro.data.model

data class UserSettings(
    val focusDuration: Int = 25, // Default focus duration in minutes
    val shortBreakDuration: Int = 5, // Default short break duration in minutes
    val longBreakDuration: Int = 15, // Default long break duration in minutes
    val sessionsBeforeLongBreak: Int = 4, // Number of sessions before a long break
    val isDarkMode: Boolean = false,
    val soundEnabled: Boolean = true,
    val notificationsEnabled: Boolean = true,
    val targetSessionsPerDay: Int = 35 // Daily target for number of sessions
) 