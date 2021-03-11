package ru.otus.otushometask1.films_list

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.like.LikeButton
import ru.otus.otushometask1.data_classes.FilmData
import ru.otus.otushometask1.R
import ru.otus.otushometask1.data.entity.Film

class FilmsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val button: Button = itemView.findViewById(R.id.detailsBtn)
    val makeFavorite: LikeButton = itemView.findViewById(R.id.makeFavorite)

    companion object {
        const val filmsUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2"
    }

    fun bind(item: Film){
//        if(item.isVisited){
//            title.setTextColor(Color.RED)
//        } else {
//            title.setTextColor(Color.WHITE)
//        }

//        makeFavorite.isLiked = item.isFavorite
        title.text = item.title

        Glide.with(image.context)
            .load("$filmsUrl${item.image}")
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_error)
            .override(image.resources.getDimensionPixelSize(R.dimen.image_size))
            .centerCrop()
            .into(image)

//        image.setImageResource(item.image)
    }
}