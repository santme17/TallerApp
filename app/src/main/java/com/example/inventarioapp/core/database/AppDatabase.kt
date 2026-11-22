package com.example.inventarioapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inventarioapp.feature.book.data.local.BookDao
import com.example.inventarioapp.feature.book.data.local.BookEntity

@Database(
    entities = [BookEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}
