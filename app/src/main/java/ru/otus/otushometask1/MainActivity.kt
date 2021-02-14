package ru.otus.otushometask1

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.otus.otushometask1.dialogs.CustomDialog
import ru.otus.otushometask1.favorites.FavoritesFragment
import ru.otus.otushometask1.favorites.FavoritesFragment.FavoritesClickListener
import ru.otus.otushometask1.film_details.FilmDetailsFragment
import ru.otus.otushometask1.films_list.FilmsAdapter
import ru.otus.otushometask1.films_list.FilmsFragment

class MainActivity : AppCompatActivity(), FilmsAdapter.NewsClickListener,
    FilmDetailsFragment.DetailsClickListener, FavoritesClickListener {
    private var favoriteItems = mutableListOf<FilmData>()
    private val noHeaderFragmentTags = arrayOf(FilmDetailsFragment.TAG)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("onCreate", "OnMainActivityCreated")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openFilmsList()
        initClickListeners()
        initBottomNavigation()
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onAttachFragment(fragment: Fragment) {
        // hide the default toolbar if the fragment has its own toolbar
        if(noHeaderFragmentTags.contains(fragment.tag.toString())) supportActionBar?.hide()
        else supportActionBar?.show()

        super.onAttachFragment(fragment)
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

    private fun initClickListeners() {
        findViewById<FloatingActionButton>(R.id.button_invite).setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                putExtra(
                    Intent.EXTRA_TEXT,
                    resources.getString(R.string.invite_message)
                )
                type = "text/plain"
                startActivity(this)
            }
        }
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
        Toast.makeText(this, "${filmItem.name} added to favorites list", Toast.LENGTH_SHORT).show()
    }
    override fun onDeleteClick(filmItem: FilmData) {
        val index = favoriteItems.indexOf(favoriteItems.find { it.id == filmItem.id })
        favoriteItems.removeAt(index)
        Toast.makeText(this, "${filmItem.name} removed from favorites list", Toast.LENGTH_SHORT).show()
    }

    // FilmDetailsFragment's click listeners
    override fun onDetailsFragmentFinished(filmItem: DetailsData) {
        Log.d("checkbox", "${filmItem.isCheckBoxSelected}")
        Log.d("comment", filmItem.comment)
    }

    // FavoritesFragment's click listeners
    override fun onFavoritesDeleteClick(position: Int) {
//        Toast.makeText(this, "${favoriteItems[position].name} removed from favorites list", Toast.LENGTH_SHORT).show()
        favoriteItems.removeAt(position)
    }
    override fun onDeleteCanceled(item: FilmData) {
       favoriteItems.add(item)
    }

}