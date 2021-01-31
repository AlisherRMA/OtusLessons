package ru.otus.otushometask1

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val button: Button = itemView.findViewById(R.id.detailsBtn)

    fun bind(item: FilmData){
        if(item.isVisited){
            title.setTextColor(Color.RED)
        } else {
            title.setTextColor(Color.WHITE)
        }
        title.text = item.name
        image.setBackgroundResource(item.image)
    }
}