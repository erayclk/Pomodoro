package com.example.pomodoro.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.pomodoro.data.model.UserSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferencesKeys {
        val FOCUS_DURATION = intPreferencesKey("focus_duration")
        val SHORT_BREAK_DURATION = intPreferencesKey("short_break_duration")
        val LONG_BREAK_DURATION = intPreferencesKey("long_break_duration")
        val SESSIONS_BEFORE_LONG_BREAK = intPreferencesKey("sessions_before_long_break")
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val TARGET_SESSIONS_PER_DAY = intPreferencesKey("target_sessions_per_day")
    }

    val settings: Flow<UserSettings> = context.dataStore.data.map { preferences ->
        UserSettings(
            focusDuration = preferences[PreferencesKeys.FOCUS_DURATION] ?: 25,
            shortBreakDuration = preferences[PreferencesKeys.SHORT_BREAK_DURATION] ?: 5,
            longBreakDuration = preferences[PreferencesKeys.LONG_BREAK_DURATION] ?: 15,
            sessionsBeforeLongBreak = preferences[PreferencesKeys.SESSIONS_BEFORE_LONG_BREAK] ?: 4,
            isDarkMode = preferences[PreferencesKeys.IS_DARK_MODE] ?: false,
            soundEnabled = preferences[PreferencesKeys.SOUND_ENABLED] ?: true,
            notificationsEnabled = preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] ?: true,
            targetSessionsPerDay = preferences[PreferencesKeys.TARGET_SESSIONS_PER_DAY] ?: 8
        )
    }

    suspend fun updateSettings(settings: UserSettings) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.FOCUS_DURATION] = settings.focusDuration
            preferences[PreferencesKeys.SHORT_BREAK_DURATION] = settings.shortBreakDuration
            preferences[PreferencesKeys.LONG_BREAK_DURATION] = settings.longBreakDuration
            preferences[PreferencesKeys.SESSIONS_BEFORE_LONG_BREAK] = settings.sessionsBeforeLongBreak
            preferences[PreferencesKeys.IS_DARK_MODE] = settings.isDarkMode
            preferences[PreferencesKeys.SOUND_ENABLED] = settings.soundEnabled
            preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] = settings.notificationsEnabled
            preferences[PreferencesKeys.TARGET_SESSIONS_PER_DAY] = settings.targetSessionsPerDay
        }
    }
} 