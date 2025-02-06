package com.example.pomodoro.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.data.model.PomodoroSession
import com.example.pomodoro.data.model.UserSettings
import com.example.pomodoro.data.repository.PomodoroRepository
import com.example.pomodoro.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class PomodoroViewModel @Inject constructor(
    private val repository: PomodoroRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _timerState = MutableStateFlow<TimerState>(TimerState.Idle)
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    private val _currentSession = MutableStateFlow<PomodoroSession?>(null)
    val currentSession: StateFlow<PomodoroSession?> = _currentSession.asStateFlow()

    private val _settings = MutableStateFlow(UserSettings())
    val settings: StateFlow<UserSettings> = _settings.asStateFlow()

    private val _remainingTime = MutableStateFlow<Long>(0)
    val remainingTime: StateFlow<Long> = _remainingTime.asStateFlow()

    private val _points = MutableStateFlow(0)
    val points: StateFlow<Int> = _points.asStateFlow()

    private val _streak = MutableStateFlow(0)
    val streak: StateFlow<Int> = _streak.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getTotalPoints().collect { points ->
                _points.value = points ?: 0
            }
        }
        viewModelScope.launch {
            repository.getMaxStreak().collect { streak ->
                _streak.value = streak ?: 0
            }
        }
        viewModelScope.launch {
            settingsRepository.settings.collect { settings ->
                _settings.value = settings
            }
        }
    }

    fun startSession() {
        if (_timerState.value !is TimerState.Running) {
            viewModelScope.launch {
                val session = PomodoroSession(
                    startTime = Calendar.getInstance().time,
                    duration = _settings.value.focusDuration,
                    points = calculatePoints(_streak.value)
                )
                val sessionId = repository.insertSession(session)
                _currentSession.value = session.copy(id = sessionId)
                _timerState.value = TimerState.Running
                _remainingTime.value = _settings.value.focusDuration * 60L
                startCountdown()
            }
        }
    }

    private fun startCountdown() {
        viewModelScope.launch {
            while (_timerState.value is TimerState.Running && _remainingTime.value > 0) {
                delay(1000) // 1 saniye bekle
                _remainingTime.value = _remainingTime.value - 1
                if (_remainingTime.value <= 0) {
                    completeSession()
                }
            }
        }
    }

    fun pauseSession() {
        if (_timerState.value is TimerState.Running) {
            _timerState.value = TimerState.Paused
        }
    }

    fun resumeSession() {
        if (_timerState.value is TimerState.Paused) {
            _timerState.value = TimerState.Running
            startCountdown()
        }
    }

    fun completeSession() {
        viewModelScope.launch {
            _currentSession.value?.let { session ->
                val updatedSession = session.copy(
                    endTime = Calendar.getInstance().time,
                    isCompleted = true
                )
                repository.updateSession(updatedSession)
                _currentSession.value = null
                _timerState.value = TimerState.Idle
                _remainingTime.value = 0
            }
        }
    }

    fun updateSettings(newSettings: UserSettings) {
        viewModelScope.launch {
            settingsRepository.updateSettings(newSettings)
            _settings.value = newSettings
        }
    }

    private fun calculatePoints(currentStreak: Int): Int {
        return when {
            currentStreak <= 1 -> 1
            currentStreak <= 3 -> 2
            currentStreak <= 5 -> 3
            else -> 5
        }
    }

    sealed class TimerState {
        object Idle : TimerState()
        object Running : TimerState()
        object Paused : TimerState()
    }
} 