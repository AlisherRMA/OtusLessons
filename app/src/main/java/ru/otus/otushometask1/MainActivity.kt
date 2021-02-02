package ru.otus.otushometask1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.otus.otushometask1.favorites.FavoritesActivity

class MainActivity : AppCompatActivity() {
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }

    private val items = mutableListOf(
        FilmData(1, "ИО", "tst 1", R.drawable.poster_1, false, false),
        FilmData(2, "Соловей", "tst 1", R.drawable.poster_2, false, false),
        FilmData(3, "Сладкие грёзы", "tst 1", R.drawable.poster_3, false, false),
        FilmData(4, "Начало", "tst 1", R.drawable.poster_4, false, false),
        FilmData(5, "Джанго освобожденный", "tst 1", R.drawable.poster_5, false, false),
        FilmData(6, "Интерстеллар", "tst 1", R.drawable.poster_6, false, false),
        FilmData(9, "Бесславные ублюдки", "tst 1", R.drawable.poster_9, false, false),
        FilmData(8, "Зеленая книга", "tst 1", R.drawable.poster_8, false, false)
    )

    private var favoriteItems = mutableListOf<FilmData>()

    companion object {
        const val EXTRA_ITEMS = "EXTRA_ITEMS"
        const val EXTRA_FAVORITES_BACK = "EXTRA_FAVORITES_BACK"

        const val START_DETAILS_ACTIVITY_REQUEST_CODE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
        initClickListeners()
        initBottomNavigation()

        getFavoriteItems()
    }

    private fun initBottomNavigation() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)

        bottomNavigationView.selectedItemId = R.id.navigation_home
        bottomNavigationView.setOnNavigationItemSelectedListener {
            Log.d("ACT_ID", it.itemId.toString());
            when (it.itemId) {
                R.id.navigation_favorites -> {
                    Intent(this, FavoritesActivity::class.java).apply {
                        putParcelableArrayListExtra(
                            FavoritesActivity.EXTRA_FAVORITE,
                            ArrayList(favoriteItems)
                        )
                        startActivity(this)
                    }
                    true
                }
                R.id.navigation_home -> true
            }
            true
        }
    }

    private fun initClickListeners() {
        findViewById<Button>(R.id.button_invite).setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                putExtra(
                    Intent.EXTRA_TEXT,
                    resources.getString(R.string.invite_message)
                );
                type = "text/plain"
                startActivity(this)
            }
        }
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = FilmsAdapter(items, object : FilmsAdapter.NewsClickListener {
            override fun onDetailsClick(filmItem: FilmData, position: Int) {
                items[position].isVisited = true;
                recyclerView.adapter?.notifyItemChanged(position)

                Intent(applicationContext, FilmDetailsActivity::class.java).apply {
                    putExtra(FilmDetailsActivity.EXTRA_DATA, filmItem)
                    startActivityForResult(this, START_DETAILS_ACTIVITY_REQUEST_CODE)
                }
            }

            override fun onFavoriteClick(filmItem: FilmData) {
                Log.d("FAV_FILM", filmItem.name)
                filmItem.isFavorite = true
                favoriteItems.add(filmItem)
            }

            override fun onDeleteClick(filmItem: FilmData) {
                // change element -> notifyDelete
            }

        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_ITEMS, java.util.ArrayList<FilmData>(items))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == START_DETAILS_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val detailsData =
                    data?.getParcelableExtra<DetailsData>(FilmDetailsActivity.EXTRA_SEND_BACK_DATA)
                detailsData?.let {
                    Log.d("checkbox", "${it.isCheckBoxSelected}")
                    Log.d("comment", it.comment)
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    private  fun getFavoriteItems(){
        intent.getParcelableArrayListExtra<FilmData>(EXTRA_FAVORITES_BACK)?.let { it ->
            it.forEachIndexed { _, element ->
                favoriteItems.add(element)
                items.forEach { mainActivityFavorite ->
                    if(mainActivityFavorite.id == element.id){
                    mainActivityFavorite.isFavorite = true
                    }
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }

        }
    }

}