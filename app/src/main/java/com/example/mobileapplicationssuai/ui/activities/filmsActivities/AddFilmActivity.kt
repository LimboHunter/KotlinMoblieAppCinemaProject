package com.example.mobileapplicationssuai.ui.activities.filmsActivities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.mobileapplicationssuai.R
import com.example.mobileapplicationssuai.ui.activities.database.AppDatabase
import model.Film

class AddFilmActivity : AppCompatActivity() {

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_shape)

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextSides = findViewById<EditText>(R.id.editTextSides)
        val editTextArea = findViewById<EditText>(R.id.editTextArea)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)

        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        buttonAdd.setOnClickListener {
            val film = Film(
                editTextName.text.toString(),
                "",
                editTextSides.text.toString(),
                editTextArea.text.toString().toDouble(),
                R.drawable.chipichapa
            )

            db.filmDao().insert(film)

            val intent = Intent(this, FilmsListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}