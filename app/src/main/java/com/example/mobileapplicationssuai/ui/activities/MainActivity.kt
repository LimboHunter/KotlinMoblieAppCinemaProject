package com.example.mobileapplicationssuai.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileapplicationssuai.R
import com.example.mobileapplicationssuai.ui.activities.filmsActivities.FilmsListActivity

class MainActivity : AppCompatActivity() {
    var group = 4117
    var studentNumber = 15
    var numberOfVariats = 32


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getVariant()

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener( View.OnClickListener {
            val intent = Intent(this, FilmsListActivity::class.java)
            startActivity(intent)
        })

    }

    fun getVariant(){
        val variant = (group + studentNumber) % numberOfVariats + 1
        println("Variant = $variant")
    }
}