package ru.otus.otushometask1

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.otus.otushometask1.dialogs.CustomDialog
import ru.otus.otushometask1.favorites.FavoritesActivity
import ru.otus.otushometask1.favorites.FavoritesFragment
import ru.otus.otushometask1.favorites.FavoritesFragment.*
import ru.otus.otushometask1.film_details.FilmDetailsFragment
import ru.otus.otushometask1.films_list.FilmsAdapter
import ru.otus.otushometask1.films_list.FilmsFragment

class MainActivity : AppCompatActivity(), FilmsAdapter.NewsClickListener,
    FilmDetailsFragment.DetailsClickListener, FavoritesClickListener {
    private var favoriteItems = mutableListOf<FilmData>()

    companion object {
        const val EXTRA_ITEMS = "EXTRA_ITEMS"
        const val EXTRA_FAVORITES_BACK = "EXTRA_FAVORITES_BACK"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("onCreate", "OnMainActivityCreated")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openFilmsList()
//        initRecycler()
//        initClickListeners()
        initBottomNavigation()


    }

    private fun openFilmsList() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, FilmsFragment.newInstance(ArrayList(favoriteItems)), FilmsFragment.TAG)
            .commit()
    }

    private fun openFilmDetails(filmData: FilmData) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                FilmDetailsFragment.newInstance(filmData),
                FilmDetailsFragment.TAG
            )
            .addToBackStack(null)
            .commit()
    }

    private fun openFavoriteFilms() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                FavoritesFragment.newInstance(ArrayList(favoriteItems)),
                FavoritesFragment.TAG
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onStop() {
        Log.d("onStop", "OnMainActivityClosed")
        super.onStop()
    }

//    private fun initClickListeners() {
//        findViewById<Button>(R.id.button_invite).setOnClickListener {
//            Intent(Intent.ACTION_SEND).apply {
//                putExtra(
//                    Intent.EXTRA_TEXT,
//                    resources.getString(R.string.invite_message)
//                );
//                type = "text/plain"
//                startActivity(this)
//            }
//        }
//    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        outState.putParcelableArrayList(EXTRA_ITEMS, java.util.ArrayList<FilmData>(items))
    }

    private fun initBottomNavigation() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_favorites -> openFavoriteFilms()
                R.id.navigation_home -> openFilmsList()
            }
            true
        }
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            val dialog: Dialog = CustomDialog(this)
            dialog.setCancelable(false)
            dialog.setOnDismissListener {
                super.onBackPressed()
            }
            dialog.show()
        }
    }

    // FilmsListFragment's click listeners
    override fun onDetailsClick(filmItem: FilmData, position: Int) = openFilmDetails(filmItem)

    override fun onFavoriteClick(filmItem: FilmData) {
        favoriteItems.add(filmItem)
    }
    override fun onDeleteClick(filmItem: FilmData) {
        val index = favoriteItems.indexOf(favoriteItems.find { it.id == filmItem.id })
        favoriteItems.removeAt(index)
    }

    // FilmDetailsFragment's click listeners
    override fun onDetailsFragmentFinished(filmItem: DetailsData) {
        Log.d("checkbox", "${filmItem.isCheckBoxSelected}")
        Log.d("comment", filmItem.comment)
    }

    // FavoritesFragment's click listeners
    override fun onFavoritesDeleteClick(position: Int) {
        favoriteItems.removeAt(position)
    }

}