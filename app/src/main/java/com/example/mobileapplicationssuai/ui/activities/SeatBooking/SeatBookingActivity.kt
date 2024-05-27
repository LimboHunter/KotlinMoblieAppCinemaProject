package com.example.mobileapplicationssuai.ui.activities.SeatBooking

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.room.Room
import com.example.mobileapplicationssuai.R
import com.example.mobileapplicationssuai.ui.activities.database.AppDatabase
import com.example.mobileapplicationssuai.ui.activities.database.FilmDao
import com.example.mobileapplicationssuai.ui.activities.filmsActivities.FilmsListActivity
import com.google.gson.Gson
import model.Film

class SeatBookingActivity : AppCompatActivity() {

    private lateinit var seatsGrid: TableLayout
    private val selectedSeats = mutableSetOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_booking)

        // Получаем JSON-строку из Intent
        val shapeJson = intent.getStringExtra("shapeJson")

        // Преобразуем JSON-строку обратно в объект Shape
        val gson = Gson()
        val film = gson.fromJson(shapeJson, Film::class.java)

        seatsGrid = findViewById(R.id.seats_grid)
        val confirmButton: Button = findViewById(R.id.confirm_button)

        // Инициализация мест
        initializeSeats(film)

        // Обработчик нажатия на кнопку подтверждения
        confirmButton.setOnClickListener {
            val db = Room.databaseBuilder(
                this,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()

            val filmDao = db.filmDao()

            confirmBooking(film, filmDao)
        }
    }

    private fun initializeSeats(film : Film) {
        val totalRows = 10
        val seatsPerRow = 10

        // Получаем список занятых мест из объекта Shape
        val occupiedSeats = film.color.trim().split(" ")

        for (row in 1..totalRows) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )

            // Добавляем номер ряда
            val rowNumber = createRowNumberView(row)
            tableRow.addView(rowNumber)

            for (seatNumber in 1..seatsPerRow) {
                val seat = createSeatView(row, seatNumber)

                // Если место занято, закрасить его красным и запретить выбирать
                val place = (row - 1) * 10 + seatNumber
                if (occupiedSeats.contains(place.toString())) {
                    seat.setBackgroundColor(Color.RED)
                    seat.isEnabled = false
                }

                tableRow.addView(seat)
            }

            seatsGrid.addView(tableRow)
        }
    }

    private fun createRowNumberView(row: Int): TextView {
        return TextView(this).apply {
            text = "Ряд $row"
            textSize = 16f
            setPadding(8)
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.BLACK)
            gravity = Gravity.CENTER
        }
    }

    private fun createSeatView(row: Int, seatNumber: Int): TextView {
        return TextView(this).apply {
            val place = (row - 1) * 10 + seatNumber

            text = "$place"
            textSize = 16f
            setPadding(8)
            setBackgroundColor(Color.LTGRAY)
            gravity = Gravity.CENTER
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f
            )

            setOnClickListener {
                toggleSeatSelection(it)
            }
        }
    }

    private fun toggleSeatSelection(view: View) {
        if (selectedSeats.contains(view)) {
            view.setBackgroundColor(Color.LTGRAY)
            selectedSeats.remove(view)
        } else {
            view.setBackgroundColor(Color.GREEN)
            selectedSeats.add(view)
        }
    }

    private fun confirmBooking(film: Film, filmDao: FilmDao) {
        // Обновляем список выбранных мест в объекте Shape
        val selectedSeatsList = selectedSeats.map { view ->
            val place = (view as TextView).text.toString()
            place
        }

        // Добавляем выбранные места к списку занятых мест в объекте Shape
        film.color += " " + selectedSeatsList.joinToString(separator = " ")

        // Обновляем объект Shape в базе данных
        filmDao.update(film)

        // Очищаем выбранные места
        selectedSeats.forEach { view ->
            view.setBackgroundColor(Color.RED)
            view.isEnabled = false
        }

        val intent = Intent(this, FilmsListActivity::class.java)
        startActivity(intent)
        finish()
    }


}
