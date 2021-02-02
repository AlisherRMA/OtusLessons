package ru.otus.otushometask1.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.otus.otushometask1.*

class FavoritesActivity : AppCompatActivity() {
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private var favoriteItems = mutableListOf<FilmData>()

    companion object {
        const val EXTRA_FAVORITE = "EXTRA_FAVORITE"
        const val EXTRA_SEND_BACK_DATA = "EXTRA_SEND_BACK_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)


        intent.getParcelableArrayListExtra<FilmData>(EXTRA_FAVORITE)?.let {
            it.forEachIndexed { index, element ->
                favoriteItems.add(index, FilmData(element.id, element.name, element.description, element.image, element.isVisited, element.isFavorite))
            }

        }

        initRecycler()
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)

        bottomNavigationView.selectedItemId =
            R.id.navigation_favorites
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    Intent(this, MainActivity::class.java).apply {
                        putParcelableArrayListExtra(
                            MainActivity.EXTRA_FAVORITES_BACK,
                            ArrayList(favoriteItems)
                        )
                        startActivity(this)
                    }
                    true
                }
                R.id.navigation_favorites -> true
            }
            false
        }
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter =
            FavoritesAdapter(favoriteItems, object : FavoritesAdapter.FavoritesClickListener {

                override fun onDeleteClick(filmItem: FilmData, position: Int) {
                    favoriteItems.removeAt(position)
                    recyclerView.adapter?.notifyItemRemoved(position)
                    recyclerView.adapter?.notifyItemRangeChanged(position, favoriteItems.size)
                }

            })

    }
}