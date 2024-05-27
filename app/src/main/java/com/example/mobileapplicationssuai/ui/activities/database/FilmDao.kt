package com.example.mobileapplicationssuai.ui.activities.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.Film

@Dao

interface FilmDao {
    @Query("SELECT * FROM film")
    fun getAll(): List<Film>

    @Insert
    fun insert(film: Film)

    @Delete
    fun delete(film: Film)

    @Update
    fun update(film: Film)
}