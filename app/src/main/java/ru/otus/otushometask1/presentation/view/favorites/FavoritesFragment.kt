package ru.otus.otushometask1.presentation.view.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.otus.otushometask1.R
import ru.otus.otushometask1.data.entity.Film
import ru.otus.otushometask1.presentation.viewmodel.FilmsListViewModel

class FavoritesFragment : Fragment() {
    private var favoriteItems = mutableListOf<Film>()
    private val viewModel: FilmsListViewModel by lazy {
        ViewModelProvider(activity!!).get(FilmsListViewModel::class.java)
    }
    private lateinit var recyclerView: RecyclerView

    companion object {
        const val TAG = "FavoritesFragment"

        fun newInstance(): FavoritesFragment {
            val args = Bundle()

            val fragment =
                FavoritesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getFavoriteFilms()
        viewModel.favoriteFilms.observe(this.viewLifecycleOwner, Observer<List<Film>> { filmsList ->
            favoriteItems.clear()
            favoriteItems.addAll(filmsList)
            recyclerView.adapter?.notifyDataSetChanged()
        })

        initRecycler(view)
    }

    private fun initRecycler(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.adapter =
            FavoritesAdapter(
                favoriteItems,
                object :
                    FavoritesAdapter.FavoritesClickListener {
                    override fun onDeleteClick(filmItem: Film, position: Int) {
                        viewModel.makeFavorite(filmItem, false)
                        recyclerView.adapter?.notifyItemRemoved(position)
                        recyclerView.adapter?.notifyItemRangeChanged(position, favoriteItems.size)

                        Snackbar.make(
                            view,
                            "${filmItem.title} removed from favorites list",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Undo") {
                                viewModel.makeFavorite(filmItem, true)
                                recyclerView.adapter?.notifyDataSetChanged()
                            }
                            .show()
                    }
                })
    }

}