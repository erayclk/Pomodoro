package com.example.pomodoro.di

import android.content.Context
import androidx.room.Room
import com.example.pomodoro.data.local.PomodoroDatabase
import com.example.pomodoro.data.local.PomodoroDao
import com.example.pomodoro.data.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun providePomodoroDatabase(
        @ApplicationContext context: Context
    ): PomodoroDatabase {
        return Room.databaseBuilder(
            context,
            PomodoroDatabase::class.java,
            "pomodoro_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providePomodoroDao(database: PomodoroDatabase): PomodoroDao {
        return database.pomodoroDao()
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        @ApplicationContext context: Context
    ): SettingsRepository {
        return SettingsRepository(context)
    }
} 