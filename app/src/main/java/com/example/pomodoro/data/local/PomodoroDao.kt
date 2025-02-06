package com.example.pomodoro.data.local

import androidx.room.*
import com.example.pomodoro.data.model.PomodoroSession
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface PomodoroDao {
    @Insert
    suspend fun insertSession(session: PomodoroSession): Long

    @Update
    suspend fun updateSession(session: PomodoroSession)

    @Query("SELECT * FROM pomodoro_sessions WHERE id = :sessionId")
    suspend fun getSessionById(sessionId: Long): PomodoroSession?

    @Query("SELECT * FROM pomodoro_sessions ORDER BY startTime DESC")
    fun getAllSessions(): Flow<List<PomodoroSession>>

    @Query("SELECT * FROM pomodoro_sessions WHERE startTime >= :startDate AND startTime < :endDate ORDER BY startTime DESC")
    fun getSessionsInTimeRange(startDate: Date, endDate: Date): Flow<List<PomodoroSession>>

    @Query("SELECT COUNT(*) FROM pomodoro_sessions WHERE isCompleted = 1 AND startTime >= :startDate AND startTime < :endDate")
    fun getCompletedSessionsCount(startDate: Date, endDate: Date): Flow<Int>

    @Query("SELECT COALESCE(SUM(points), 0) FROM pomodoro_sessions WHERE isCompleted = 1")
    fun getTotalPoints(): Flow<Int>

    @Query("SELECT COALESCE(MAX(streak), 0) FROM pomodoro_sessions WHERE isCompleted = 1")
    fun getMaxStreak(): Flow<Int>
} 