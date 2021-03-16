package ru.otus.otushometask1.presentation.view.films_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.otus.otushometask1.R
import ru.otus.otushometask1.data.entity.Film
import ru.otus.otushometask1.data_classes.FilmData
import ru.otus.otushometask1.presentation.viewmodel.FilmsListViewModel

class FilmsFragment : Fragment() {

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
    private var films = mutableListOf<Film>()
    private var isLoading = false

    private val viewModel: FilmsListViewModel by lazy {
        ViewModelProvider(activity!!).get(FilmsListViewModel::class.java)
    }
    private lateinit var recyclerView: RecyclerView
    private  lateinit var progressBar: View

    companion object {
        const val TAG = "FilmsListFragment"
        const val EXTRA_FAVORITE = "EXTRA_FAVORITE"

        fun newInstance(favoriteFilms: ArrayList<FilmData>): FilmsFragment {
            val args = Bundle()
            args.putParcelableArrayList(EXTRA_FAVORITE, favoriteFilms)

            val fragment =
                FilmsFragment()
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
        initRecyclerView()
        progressBar = view.findViewById(R.id.progressBar)


        viewModel.films.observe(this.viewLifecycleOwner, Observer<List<Film>> { filmsList ->
            adapter.setItems(filmsList)
            films.addAll(filmsList)
            isLoading = false
            recyclerView.adapter?.notifyDataSetChanged()
            progressBar.visibility = View.INVISIBLE
        })
        viewModel.error.observe(
            this.viewLifecycleOwner,
            Observer<String> { error -> Toast.makeText(context, error, Toast.LENGTH_SHORT).show() })

        viewModel.getFilms(true)

        getFavoriteFilms()
        addOnScrollListener()
    }

    private val adapter: FilmsAdapter by lazy {
        FilmsAdapter(object :
            FilmsAdapter.NewsClickListener {
            override fun onDetailsClick(filmItem: Film, position: Int) {
//                items[position].isVisited = true
//                recyclerView.adapter?.notifyItemChanged(position)
                Toast.makeText(requireContext(), filmItem.title, Toast.LENGTH_SHORT).show()
                listener?.onDetailsClick(filmItem, position)
            }

            override fun onFavoriteClick(filmItem: FilmData) {
                filmItem.isFavorite = true
                recyclerView.adapter?.notifyDataSetChanged()
                listener?.onFavoriteClick(filmItem)
            }

            override fun onDeleteClick(filmItem: FilmData) {
                filmItem.isFavorite = false
                recyclerView.adapter?.notifyDataSetChanged()
                listener?.onDeleteClick(filmItem)
            }
        })
    }

    private fun initRecyclerView() {
        recyclerView = view!!.findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
    }

    private fun getFavoriteFilms() {
        arguments?.getParcelableArrayList<FilmData>(EXTRA_FAVORITE)?.let { receivedArray ->
            receivedArray.forEachIndexed { _, element ->
                items.forEach { if (it.id == element.id) it.isFavorite = true }
            }
        }
    }

    private fun addOnScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                if (!isLoading  && layoutManager.findLastVisibleItemPosition() >= films.size-1) {
                    isLoading = true
                    progressBar.visibility = View.VISIBLE
                    viewModel.getFilms(false)
                }
            }
        })
    }
}