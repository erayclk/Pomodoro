package com.example.pomodoro.data.repository

import com.example.pomodoro.data.local.PomodoroDao
import com.example.pomodoro.data.model.PomodoroSession
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PomodoroRepository @Inject constructor(
    private val pomodoroDao: PomodoroDao
) {
    suspend fun insertSession(session: PomodoroSession): Long {
        return pomodoroDao.insertSession(session)
    }

    suspend fun updateSession(session: PomodoroSession) {
        pomodoroDao.updateSession(session)
    }

    suspend fun getSessionById(sessionId: Long): PomodoroSession? {
        return pomodoroDao.getSessionById(sessionId)
    }

    fun getAllSessions(): Flow<List<PomodoroSession>> {
        return pomodoroDao.getAllSessions()
    }

    fun getSessionsInTimeRange(startDate: Date, endDate: Date): Flow<List<PomodoroSession>> {
        return pomodoroDao.getSessionsInTimeRange(startDate, endDate)
    }

    fun getCompletedSessionsCount(startDate: Date, endDate: Date): Flow<Int> {
        return pomodoroDao.getCompletedSessionsCount(startDate, endDate)
    }

    fun getTotalPoints(): Flow<Int> {
        return pomodoroDao.getTotalPoints()
    }

    fun getMaxStreak(): Flow<Int> {
        return pomodoroDao.getMaxStreak()
    }
} 