package com.example.mobileapplicationssuai.ui.activities.filmsActivities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.mobileapplicationssuai.R
import com.example.mobileapplicationssuai.databinding.ActivityShapesListBinding
import com.example.mobileapplicationssuai.ui.activities.MainActivity
import com.example.mobileapplicationssuai.ui.activities.RecyclerPack.FilmsAdapter
import com.example.mobileapplicationssuai.ui.activities.database.AppDatabase
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import model.Film


class FilmsListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var adapter = FilmsAdapter()
    lateinit var rcView: RecyclerView
    lateinit var binding: ActivityShapesListBinding

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShapesListBinding.inflate(layoutInflater)
        rcView = binding.rcView as RecyclerView
        setContentView(binding.root)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.include)


        setSupportActionBar(toolbar)

        navigationView.bringToFront()
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

/*        db.filmDao().insert(
            Film(
                "Iron Man", "", "Миллиардер-изобретатель " +
                        "Тони Старк попадает в плен к Афганским террористам, которые пытаются заставить " +
                        "его создать оружие массового поражения. В тайне от своих захватчиков Старк " +
                        "конструирует высокотехнологичную киберброню, которая помогает ему сбежать. " +
                        "Однако по возвращении в США он узнаёт, что в совете директоров его фирмы " +
                        "плетётся заговор, чреватый страшными последствиями. Используя своё последнее " +
                        "изобретение, Старк пытается решить проблемы своей компании радикально...",
                8.0, R.drawable.iron_man
            )
        )
        db.filmDao().insert(Film("Халк", "", "Доктор Брюс Бэннер (ученый, работающий над " +
                "изобретением новой бомбы) подвергся воздействию гамма-лучей и превратился в Халка - существо " +
                "невероятной физической силы, которое в состоянии ярости становится огромным зеленым монстром.\n" +
                "\n" +
                "Его преследуют военные под предводительством генерала Росса, ему приходится бежать через" +
                " всю страну. В конце концов, у него возникает любовная история с генеральской дочкой Бетти...",
            6.2, R.drawable.hulk))
        db.filmDao().insert(Film("The Dark Knight", "", "Бэтмен поднимает ставки в войне с" +
                " криминалом. С помощью лейтенанта Джима Гордона и прокурора Харви Дента он намерен очистить " +
                "улицы Готэма от преступности. Сотрудничество оказывается эффективным, но скоро они обнаружат " +
                "себя посреди хаоса, развязанного восходящим криминальным гением, известным напуганным горожанам " +
                "под именем Джокер.", 8.5, R.drawable.batman))
        db.filmDao().insert(Film("2012", "", "Согласно календарю индейцев Майя, " +
                "в 2012 году планеты солнечной системы окажутся на одной линии друг с другом, что " +
                "приведет к глобальным природным катаклизмам: сильнейшие землетрясения, цунами и " +
                "извержения вулканов превратят страны и целые континенты в руины. Недавно ученые " +
                "подтвердили, что этот миф может стать реальностью.", 6.9, R.drawable.two_k_twelve))
        db.filmDao().insert(Film("Нечто", "", "Команде ученых американской исследовательской " +
                "станции в Антарктике предстоит столкнуться с необъяснимым кошмаром. Отрезанные от остального мира " +
                "полярники вступают в схватку с инопланетной тварью, способной принимать обличье земных существ.", 7.9, R.drawable.the_thing))*/
        getFiguresFromDb()

        adapter.setOnItemClickListener(object : FilmsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedFilm = adapter.shapesList[position]
                val intent = Intent(this@FilmsListActivity, FilmActivity::class.java)
                val gson = Gson()
                val filmJson = gson.toJson(selectedFilm)

                intent.putExtra("filmJson", filmJson)
                startActivity(intent)
            }
        })

        val addShapeButton = findViewById<Button>(R.id.addShapeButton)

        addShapeButton.setOnClickListener {
            val intent = Intent(this, AddFilmActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD_SHAPE)
        }

        init()
    }

    private fun init() {
        rcView.layoutManager = GridLayoutManager(this@FilmsListActivity, 2)
        rcView.adapter = adapter
    }

    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ADD_SHAPE && resultCode == Activity.RESULT_OK) {
            getFiguresFromDb()
        }
    }

    @SuppressLint("Range")
    fun getFiguresFromDb() {
        val filmDao = db.filmDao()
        val films: List<Film> = filmDao.getAll()


        for (film in films) {

            Log.i("DATABASE_TAG", film.imageResId.toString())
            adapter.addShape(film)
            Log.d("ShapesListActivity", "Added new shape: $film")
            adapter.notifyDataSetChanged()

        }

    }

    companion object {
        private const val REQUEST_ADD_SHAPE = 1
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_list -> {

            }

            R.id.nav_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_create -> {
                findViewById<Button>(R.id.addShapeButton).performClick()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}