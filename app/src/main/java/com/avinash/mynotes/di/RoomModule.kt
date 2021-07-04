package com.avinash.mynotes.di

import android.content.Context
import androidx.room.Room
import com.avinash.mynotes.room.AppDatabase
import com.avinash.mynotes.room.NotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "Notes_Database").build()
    }

    @Provides
    fun provideNotesDao(appDatabase : AppDatabase): NotesDao {
        return appDatabase.getNotesDao()
    }
}