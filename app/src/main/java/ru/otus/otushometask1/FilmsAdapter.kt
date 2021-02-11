package ru.otus.otushometask1

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.like.LikeButton
import com.like.OnLikeListener


class FilmsAdapter(
    private val items: List<FilmData>,
    private val clickListener: NewsClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_films, parent, false)
        return FilmsVH(view)
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
//                holder.title.setTextColor(Color.GREEN)
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