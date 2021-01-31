package ru.otus.otushometask1

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }

    private val items = mutableListOf(
        FilmData(1, "ИО", "tst 1", R.drawable.poster_1, false),
        FilmData(2, "Соловей", "tst 1", R.drawable.poster_2, false),
        FilmData(3, "Сладкие грёзы", "tst 1", R.drawable.poster_3, false),
        FilmData(4, "Начало", "tst 1", R.drawable.poster_4, false),
        FilmData(5, "Джанго освобожденный", "tst 1", R.drawable.poster_5, false),
        FilmData(6, "Интерстеллар", "tst 1", R.drawable.poster_6, false),
        FilmData(8, "Зеленая книга", "tst 1", R.drawable.poster_8, false),
        FilmData(9, "Бесславные ублюдки", "tst 1", R.drawable.poster_9, false)
    )

    companion object {
        const val EXTRA_ITEMS = "EXTRA_ITEMS"

        const val START_DETAILS_ACTIVITY_REQUEST_CODE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
        initClickListeners()
        initBottomNavigation()
    }

    private fun initBottomNavigation(){
       val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)

        bottomNavigationView.selectedItemId = R.id.navigation_home
        bottomNavigationView.setOnNavigationItemSelectedListener {
            Log.d("ACT_ID", it.itemId.toString());
            when (it.itemId) {
                R.id.navigation_favorites -> {
                    Intent(this, FavoritesActivity::class.java).apply {
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
                // change element -> notifyChange
            }

            override fun onDeleteClick(filmItem: FilmData) {
                // change element -> notifyDelete
            }

        })

//        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (layoutManager.findLastVisibleItemPosition() == items.size) {
//                    repeat(4) {
//                        items.add(FilmData("---", "---", Color.BLACK))
//                    }
//
//                    recyclerView.adapter?.notifyItemRangeInserted(items.size - 4, 4)
//                }
//            }
//        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_ITEMS, java.util.ArrayList<FilmData>(items))
//        outState.putInt(EXTRA_COLOR_VIEW_1, textView1.currentTextColor)
//        state.putParcelable(LIST_STATE_KEY, mListState);
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

    private fun onButtonClicked(buttonId: Int, textViewId: Int, filmId: Int) {
        findViewById<Button>(buttonId).setOnClickListener {
            Intent(this, FilmDetailsActivity::class.java).apply {
//                findViewById<TextView>(textViewId).setTextColor(
//                    ContextCompat.getColor(
//                        this@MainActivity,
//                        R.color.purple_500
//                    )
//                )

                putExtra(FilmDetailsActivity.EXTRA_DATA, items[filmId])

                startActivityForResult(this, START_DETAILS_ACTIVITY_REQUEST_CODE)
            }
        }
    }

}