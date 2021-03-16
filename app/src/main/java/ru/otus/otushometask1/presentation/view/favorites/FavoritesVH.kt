package ru.otus.otushometask1.presentation.view.favorites

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.otus.otushometask1.data_classes.FilmData
import ru.otus.otushometask1.R

class FavoritesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.fav_textview)
    val image: ImageView = itemView.findViewById(R.id.fav_image)
    val deleteBtn: ImageView = itemView.findViewById(R.id.fav_delete)

    fun bind(item: FilmData){
        title.text = item.name
        image.setBackgroundResource(item.image)
    }
}