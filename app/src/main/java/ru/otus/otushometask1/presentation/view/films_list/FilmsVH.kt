package ru.otus.otushometask1.presentation.view.films_list

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.like.LikeButton
import ru.otus.otushometask1.R
import ru.otus.otushometask1.data.database.entity.Film

class FilmsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val button: Button = itemView.findViewById(R.id.detailsBtn)
    val makeFavorite: LikeButton = itemView.findViewById(R.id.makeFavorite)
    var progressBar: ProgressBar = itemView.findViewById(R.id.progress)

    companion object {
        const val filmsUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2"
    }

    fun bind(item: Film){
//        if(item.isVisited){
//            title.setTextColor(Color.RED)
//        } else {
//            title.setTextColor(Color.WHITE)
//        }

        makeFavorite.isLiked = item.isLiked
        title.text = item.title
        progressBar.visibility = View.VISIBLE

        Glide.with(image.context)
            .load("$filmsUrl${item.image}")
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_error)
            .override(image.resources.getDimensionPixelSize(R.dimen.image_size))
            .centerCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

            })
            .into(image)
    }
}