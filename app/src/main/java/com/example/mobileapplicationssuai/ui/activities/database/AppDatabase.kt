package com.example.mobileapplicationssuai.ui.activities.database

import androidx.room.Database
import androidx.room.RoomDatabase
import model.Film

@Database(entities = [Film::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao

}