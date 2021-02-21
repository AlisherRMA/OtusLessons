package ru.otus.otushometask1.films_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_films_list.*
import ru.otus.otushometask1.data_classes.FilmData
import ru.otus.otushometask1.R
import java.lang.Exception

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

    companion object {
        const val TAG = "FilmsListFragment"
        const val EXTRA_FAVORITE = "EXTRA_FAVORITE"

        fun newInstance(favoriteFilms: ArrayList<FilmData>):FilmsFragment {
            val args = Bundle()
            args.putParcelableArrayList(EXTRA_FAVORITE, favoriteFilms)

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
//        retainInstance = true
        return inflater.inflate(R.layout.fragment_films_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView(view)
        getFavoriteFilms()
    }

    private fun initRecyclerView(view: View) {
        view.findViewById<RecyclerView>(R.id.recyclerView).adapter = FilmsAdapter(
            items,
            object :
                FilmsAdapter.NewsClickListener {
                override fun onDetailsClick(filmItem: FilmData, position: Int) {
                    items[position].isVisited = true
                    recyclerView.adapter?.notifyItemChanged(position)
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

   private fun getFavoriteFilms(){
       arguments?.getParcelableArrayList<FilmData>(EXTRA_FAVORITE)?.let { receivedArray ->
           receivedArray.forEachIndexed { _, element ->
               items.forEach { if (it.id == element.id) it.isFavorite = true }
           }
       }
    }
}