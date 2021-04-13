package ru.otus.otushometask1.presentation.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.otus.otushometask1.R
import ru.otus.otushometask1.data.database.entity.Film
import ru.otus.otushometask1.data.network.dto.DetailsData
import ru.otus.otushometask1.presentation.view.dialogs.CustomDialog
import ru.otus.otushometask1.presentation.view.favorites.FavoritesFragment
import ru.otus.otushometask1.presentation.view.film_details.FilmDetailsFragment
import ru.otus.otushometask1.presentation.view.films_list.FilmsAdapter
import ru.otus.otushometask1.presentation.view.films_list.FilmsFragment
import ru.otus.otushometask1.presentation.viewmodel.FilmsListViewModel
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity(), FilmsAdapter.NewsClickListener,
    FilmDetailsFragment.DetailsClickListener {
    private val noHeaderFragmentTags = arrayOf(FilmDetailsFragment.TAG)

    private val viewModel: FilmsListViewModel by lazy {
        ViewModelProvider(this).get(FilmsListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("onCreate", "OnMainActivityCreated")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openFilmsList()
        initClickListeners()
        initBottomNavigation()

        checkIfActivityStartedFromNotification()
    }


    override fun onAttachFragment(fragment: Fragment) {
        Log.d("FragmentAttached", "FragmentAttached")
        // hide the default toolbar if the fragment has its own toolbar
        if (noHeaderFragmentTags.contains(fragment.tag.toString())) supportActionBar?.hide()
        else supportActionBar?.show()

        super.onAttachFragment(fragment)
    }

    private fun openFilmsList() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                FilmsFragment.newInstance(),
                FilmsFragment.TAG
            )
            .commit()
    }

    private fun openFilmDetails(filmData: Film) {
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
                FavoritesFragment.newInstance(),
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
            val dialog: Dialog =
                CustomDialog(this)
            dialog.setCancelable(false)
            dialog.setOnDismissListener {
                super.onBackPressed()
            }
            dialog.show()
        }
    }

    @SuppressLint("LongLogTag")
    private fun checkIfActivityStartedFromNotification() {
        val firebaseFilmId = intent.extras?.get("film_id")
        if (firebaseFilmId != null) {
            val film = viewModel.getFilmById(firebaseFilmId.toString().toInt())
            if (film != null) {
                Timer().schedule(timerTask {
                    openFilmDetails(film)
                }, 500)
            }
        }
        val bundle = intent.getBundleExtra("filmBundle")
        if (bundle != null) {
            val film = bundle.getParcelable<Film>("film") as Film
            Timer().schedule(timerTask {
                openFilmDetails(film)
            }, 500)
        }
    }

    // FilmsListFragment's click listeners
    override fun onDetailsClick(filmItem: Film, position: Int) = openFilmDetails(filmItem)
    override fun onFavoriteClick(filmItem: Film) {}
    override fun onDeleteClick(filmItem: Film) {}

    // FilmDetailsFragment's click listeners
    override fun onDetailsFragmentFinished(filmItem: DetailsData) {
        Log.d("checkbox", "${filmItem.isCheckBoxSelected}")
        Log.d("comment", filmItem.comment)
    }


}
