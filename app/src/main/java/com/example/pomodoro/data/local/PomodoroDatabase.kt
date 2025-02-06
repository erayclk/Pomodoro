package com.example.pomodoro.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pomodoro.data.model.PomodoroSession

@Database(
    entities = [PomodoroSession::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PomodoroDatabase : RoomDatabase() {
    abstract fun pomodoroDao(): PomodoroDao
} 