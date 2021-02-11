package ru.otus.otushometask1

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.otus.otushometask1.dialogs.CustomDialog
import ru.otus.otushometask1.favorites.FavoritesActivity

class MainActivity : AppCompatActivity() {
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }

    private val items = mutableListOf(
        FilmData(
            1,
            "IO",
            "As a young scientist searches for a way to save a dying Earth, she finds a connection with a man who's racing to catch the last shuttle off the planet.",
            R.drawable.poster_1,
            false,
            false
        ),
        FilmData(
            2,
            "The Nightingale",
            "Set in 1825, Clare, a young Irish convict woman, chases a British officer through the rugged Tasmanian wilderness, bent on revenge for a terrible act of violence he committed against her family. On the way she enlists the services of an Aboriginal tracker named Billy, who is also marked by trauma from his own violence-filled past.",
            R.drawable.poster_2,
            false,
            false
        ),
        FilmData(
            3,
            "Sweet dreams",
            "The story of Patsy Cline, the velvet-voiced country music singer who died in a tragic plane crash at the height of her fame.",
            R.drawable.poster_3,
            false,
            false
        ),
        FilmData(
            4,
            "Inception",
            "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
            R.drawable.poster_4,
            false,
            false
        ),
        FilmData(
            5,
            "Django Unchained",
            "With the help of a German bounty hunter, a freed slave sets out to rescue his wife from a brutal Mississippi plantation owner.",
            R.drawable.poster_5,
            false,
            false
        ),
        FilmData(
            6,
            "Interstellar ",
            "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
            R.drawable.poster_6,
            false,
            false
        ),
        FilmData(
            9,
            "Inglourious Basterds",
            "In Nazi-occupied France during World War II, a plan to assassinate Nazi leaders by a group of Jewish U.S. soldiers coincides with a theatre owner's vengeful plans for the same.",
            R.drawable.poster_9,
            false,
            false
        ),
        FilmData(
            8,
            "Green Book",
            "A working-class Italian-American bouncer becomes the driver of an African-American classical pianist on a tour of venues through the 1960s American South.",
            R.drawable.poster_8,
            false,
            false
        )
    )

    private var favoriteItems = mutableListOf<FilmData>()

    companion object {
        const val EXTRA_ITEMS = "EXTRA_ITEMS"
        const val EXTRA_FAVORITES_BACK = "EXTRA_FAVORITES_BACK"

        const val START_DETAILS_ACTIVITY_REQUEST_CODE = 0
        const val START_FAVORITES_ACTIVITY_REQUEST_CODE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("onCreate", "OnMainActivityCreated")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
        initClickListeners()
        initBottomNavigation()


    }

    override fun onStop() {
        Log.d("onStop", "OnMainActivityClosed")
        super.onStop()
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
                Log.d("fav_ITEMS_LENGTH", favoriteItems.size.toString())
                filmItem.isFavorite = true
                favoriteItems.add(filmItem)
                recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onDeleteClick(filmItem: FilmData) {
                filmItem.isFavorite = false
                val index = favoriteItems.indexOf(favoriteItems.find {it.id == filmItem.id})
                favoriteItems.removeAt(index)
                recyclerView.adapter?.notifyDataSetChanged()
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
        } else if (requestCode == START_FAVORITES_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                getFavoriteItems(data)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun initBottomNavigation() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)

        bottomNavigationView.selectedItemId = R.id.navigation_home
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_favorites -> {
                    Intent(this, FavoritesActivity::class.java).apply {
                        putParcelableArrayListExtra(
                            FavoritesActivity.EXTRA_FAVORITE,
                            ArrayList(favoriteItems)
                        )
                        startActivityForResult(this, START_FAVORITES_ACTIVITY_REQUEST_CODE)
                    }
                    true
                }
                R.id.navigation_home -> true
            }
            true
        }
    }

    private fun getFavoriteItems(data: Intent?) {
        favoriteItems.clear()
        items.forEach { it.isFavorite = false }
        data?.getParcelableArrayListExtra<FilmData>(EXTRA_FAVORITES_BACK)?.let { receivedArray ->

                receivedArray.forEachIndexed { _, element ->
                    favoriteItems.add(element)
                    Log.d("NAME", element.name)
                    items.forEach { if (it.id == element.id) it.isFavorite = true }
                }


            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    override fun onBackPressed() {
        val dialog: Dialog = CustomDialog(this)
        dialog.setCancelable(false)
        dialog.setOnDismissListener {
            super.onBackPressed()
        }

        dialog.show()
    }

}