package ru.otus.otushometask1.presentation.view.films_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.otus.otushometask1.data_classes.FilmData
import ru.otus.otushometask1.R
import ru.otus.otushometask1.data.entity.Film
import java.util.ArrayList


class FilmsAdapter(
//    private val items: List<FilmData>,
    private val clickListener: NewsClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<Film>()

    fun setItems(repos: List<Film>) {
//        items.clear()
        items.addAll(repos)

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FilmsVH(
            layoutInflater.inflate(
                R.layout.item_films,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FilmsVH) {
            val item = items[position]
            holder.bind(item)

//            holder.makeFavorite.setOnLikeListener(object : OnLikeListener {
//                override fun liked(likeButton: LikeButton) {
//                    Log.d("ADAPTER", item.name)
//                    clickListener.onFavoriteClick(item)
//                }
//                override fun unLiked(likeButton: LikeButton) {
//                    clickListener.onDeleteClick(item)
//                }
//            })
//
            holder.button.setOnClickListener {
                clickListener.onDetailsClick(item, position)
            }


        }
    }

    interface NewsClickListener {
        fun onDetailsClick(filmItem: Film, position: Int)
        fun onFavoriteClick(filmItem: FilmData)
        fun onDeleteClick(filmItem: FilmData)
    }
}