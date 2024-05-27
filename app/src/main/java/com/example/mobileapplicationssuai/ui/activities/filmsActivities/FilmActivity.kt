package com.example.mobileapplicationssuai.ui.activities.filmsActivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.mobileapplicationssuai.R
import com.example.mobileapplicationssuai.ui.activities.SeatBooking.SeatBookingActivity
import com.example.mobileapplicationssuai.ui.activities.database.AppDatabase
import com.google.gson.Gson
import model.Film

class FilmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shapes)

        val shapeJson = intent.getStringExtra("filmJson")
        val gson = Gson()
        val film = gson.fromJson(shapeJson, Film::class.java)
        val buttonDel = findViewById<Button>(R.id.buttonDelete)
        val buttonBook = findViewById<Button>(R.id.buttonBookSeats)
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        title = ""

        aboutFigure(film)

        buttonDel.setOnClickListener{
            db.filmDao().delete(film)

            val intent = Intent(this, FilmsListActivity::class.java)
            startActivity(intent)

            finish()
        }
        buttonBook.setOnClickListener{
            val intent = Intent(this, SeatBookingActivity::class.java)
            // Помещаем объект фигуры в Intent как JSON-строку
            intent.putExtra("shapeJson", gson.toJson(film))
            startActivity(intent)
            finish()
        }
    }

    fun aboutFigure(film: Film){
        findViewById<TextView>(R.id.textViewShapeName).text = "${film.name}"
        findViewById<TextView>(R.id.textViewShapeSides).text = "Описание: ${film.description}"
        findViewById<TextView>(R.id.textViewShapeArea).text = "Рейтинг: ${film.rating}"
        findViewById<ImageView>(R.id.imageViewShape).setImageResource(film.imageResId)
    }
}