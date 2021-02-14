package ru.otus.otushometask1.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.otus.otushometask1.FilmData
import ru.otus.otushometask1.R

class FavoritesFragment : Fragment() {
    private var favoriteItems = mutableListOf<FilmData>()

    companion object {
        const val TAG = "FavoritesFragment"
        const val EXTRA_FAVORITE = "EXTRA_FAVORITE"

        fun newInstance(favoriteFilms: ArrayList<FilmData>): FavoritesFragment {
            val args = Bundle()
            args.putParcelableArrayList(EXTRA_FAVORITE, favoriteFilms)

            val fragment = FavoritesFragment()
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

        arguments?.getParcelableArrayList<FilmData>(EXTRA_FAVORITE)?.let {
            favoriteItems = it
        }

        initRecycler(view)
    }

    private fun initRecycler(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.adapter =
            FavoritesAdapter(
                LayoutInflater.from(requireContext()),
                favoriteItems,
                object : FavoritesAdapter.FavoritesClickListener {
                    override fun onDeleteClick(filmItem: FilmData, position: Int) {
                        favoriteItems.removeAt(position)
                        recyclerView.adapter?.notifyItemRemoved(position)
                        recyclerView.adapter?.notifyItemRangeChanged(position, favoriteItems.size)

                        (activity as? FavoritesClickListener)?.onFavoritesDeleteClick(position)

                        Snackbar.make(view, "${filmItem.name} removed from favorites list", Snackbar.LENGTH_LONG)
                            .setAction("Undo") {
                                favoriteItems.add(filmItem)
                                (activity as? FavoritesClickListener)?.onDeleteCanceled(filmItem)
                                recyclerView.adapter?.notifyDataSetChanged()
                            }
                            .show()
                    }
                })
    }

    interface FavoritesClickListener {
        fun onFavoritesDeleteClick(position: Int)
        fun onDeleteCanceled(item: FilmData)
    }
}