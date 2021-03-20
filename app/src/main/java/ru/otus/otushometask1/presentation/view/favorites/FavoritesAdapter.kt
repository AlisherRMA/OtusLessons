package ru.otus.otushometask1.presentation.view.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.otus.otushometask1.R
import ru.otus.otushometask1.data.entity.Film

class FavoritesAdapter(
    private val items: MutableList<Film>,
    private val clickListener: FavoritesClickListener
) : RecyclerView.Adapter<FavoritesVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_favorites, parent, false)
        return FavoritesVH(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FavoritesVH, position: Int) {
            val item = items[position]
            holder.bind(item)

        holder.deleteBtn.setOnClickListener{
            clickListener.onDeleteClick(item, position)
        }
    }

    interface FavoritesClickListener {
        fun onDeleteClick(filmItem: Film, position: Int)
    }
}