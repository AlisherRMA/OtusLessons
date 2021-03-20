package ru.otus.otushometask1.presentation.view.films_list

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.otus.otushometask1.R
import ru.otus.otushometask1.data.entity.Film
import ru.otus.otushometask1.presentation.viewmodel.FilmsListViewModel
import java.util.*
import kotlin.concurrent.timerTask

class FilmsFragment : Fragment() {

    private var films = mutableListOf<Film>()
    private var isLoading = false

    private val viewModel: FilmsListViewModel by lazy {
        ViewModelProvider(activity!!).get(FilmsListViewModel::class.java)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: View

    companion object {
        const val TAG = "FilmsListFragment"

        fun newInstance(): FilmsFragment {
            val args = Bundle()

            val fragment = FilmsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var listener: FilmsAdapter.NewsClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (activity is FilmsAdapter.NewsClickListener) {
            listener = activity as FilmsAdapter.NewsClickListener
        } else {
            throw Exception("Activity must implement OnNewsClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_films_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getFilms(true)
        initRecyclerView()
        progressBar = view.findViewById(R.id.progressBar)

        viewModel.films.observe(this.viewLifecycleOwner, Observer<List<Film>> { filmsList ->
            films.clear()
            films.addAll(filmsList)
            adapter.setItems(filmsList)
            progressBar.visibility = View.INVISIBLE
            Timer().schedule(timerTask {
                isLoading = false
            }, 2000)

        })
        viewModel.error.observe(
            this.viewLifecycleOwner,
            Observer<String> {
                Snackbar.make(
                    view,
                    "Произошла ошибка во время получения данных",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Повторить") {
                        viewModel.getFilms(true)
                    }
                    .show()
            })

        addOnScrollListener()
    }

    private val adapter: FilmsAdapter by lazy {
        FilmsAdapter(object :
            FilmsAdapter.NewsClickListener {
            override fun onDetailsClick(filmItem: Film, position: Int) {
                listener?.onDetailsClick(filmItem, position)
            }

            override fun onFavoriteClick(filmItem: Film) = viewModel.makeFavorite(filmItem, true)

            override fun onDeleteClick(filmItem: Film) = viewModel.makeFavorite(filmItem, false)
        })
    }

    private fun initRecyclerView() {
        recyclerView = view!!.findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
    }

    private fun addOnScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                if (!isLoading && layoutManager.findLastVisibleItemPosition() >= films.size - 1) {
                    isLoading = true
                    progressBar.visibility = View.VISIBLE
                    viewModel.getFilms(false)
                }
            }
        })
    }
}