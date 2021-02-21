package ru.otus.otushometask1.films_list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.like.LikeButton
import com.like.OnLikeListener
import ru.otus.otushometask1.data_classes.FilmData
import ru.otus.otushometask1.R


class FilmsAdapter(
    private val items: List<FilmData>,
    private val clickListener: NewsClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FilmsVH(layoutInflater.inflate(R.layout.item_films, parent, false))
//        return FilmsVH(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FilmsVH) {
            val item = items[position]
            holder.bind(item)

            holder.makeFavorite.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton) {
                    Log.d("ADAPTER", item.name)
                    clickListener.onFavoriteClick(item)
                }
                override fun unLiked(likeButton: LikeButton) {
                    clickListener.onDeleteClick(item)
                }
            })

            holder.button.setOnClickListener {
                clickListener.onDetailsClick(item, position)
            }


        }
    }

    interface NewsClickListener {
        fun onDetailsClick(filmItem: FilmData, position: Int)
        fun onFavoriteClick(filmItem: FilmData)
        fun onDeleteClick(filmItem: FilmData)
    }
}