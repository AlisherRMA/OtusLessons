package ru.otus.otushometask1.presentation.view.favorites

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import ru.otus.otushometask1.R
import ru.otus.otushometask1.data.database.entity.Film
import ru.otus.otushometask1.presentation.view.films_list.FilmsVH

class FavoritesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.fav_textview)
    val image: ImageView = itemView.findViewById(R.id.fav_image)
    val deleteBtn: ImageView = itemView.findViewById(R.id.fav_delete)

    fun bind(item: Film) {
        title.text = item.title

        Glide.with(image.context)
            .load("${FilmsVH.filmsUrl}${item.image}")
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
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .into(image)
    }
}