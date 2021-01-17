package ru.otus.otushometask1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_COLOR_VIEW_1 = "EXTRA_COLOR_VIEW_1"
        const val EXTRA_COLOR_VIEW_2 = "EXTRA_COLOR_VIEW_2"
        const val EXTRA_COLOR_VIEW_3 = "EXTRA_COLOR_VIEW_3"

        const val START_DETAILS_ACTIVITY_REQUEST_CODE = 0
    }

    private val textView1 by lazy {
        findViewById<TextView>(R.id.textView)
    }
    private val textView2 by lazy {
        findViewById<TextView>(R.id.textView2)
    }
    private val textView3 by lazy {
        findViewById<TextView>(R.id.textView3)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onButtonClicked(R.id.button1, R.id.textView, 1)
        onButtonClicked(R.id.button2, R.id.textView2, 2)
        onButtonClicked(R.id.button3, R.id.textView3, 3)

        savedInstanceState?.getInt(EXTRA_COLOR_VIEW_1)?.let {
            textView1.setTextColor(it)
        }
        savedInstanceState?.getInt(EXTRA_COLOR_VIEW_2)?.let {
            textView2.setTextColor(it)
        }
        savedInstanceState?.getInt(EXTRA_COLOR_VIEW_3)?.let {
            textView3.setTextColor(it)
        }

        findViewById<Button>(R.id.button_invite).setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                putExtra(
                    Intent.EXTRA_TEXT,
                    "I have created a new android application. Please join me, I have cookies :)"
                );
                type = "text/plain"
                startActivity(this)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(EXTRA_COLOR_VIEW_1, textView1.currentTextColor)
        outState.putInt(EXTRA_COLOR_VIEW_2, textView2.currentTextColor)
        outState.putInt(EXTRA_COLOR_VIEW_3, textView3.currentTextColor)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == START_DETAILS_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val detailsData =
                    data?.getParcelableExtra<DetailsData>(FilmDetailsActivity.EXTRA_SEND_BACK_DATA)
                detailsData?.let {
                    Toast.makeText(
                        this,
                        "checkbox: ${it.isCheckBoxSelected}, comment: ${it.comment}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun onButtonClicked(buttonId: Int, textViewId: Int, filmId: Int) {
        findViewById<Button>(buttonId).setOnClickListener {
            Intent(this, FilmDetailsActivity::class.java).apply {
                findViewById<TextView>(textViewId).setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.purple_500
                    )
                )

                putExtra(FilmDetailsActivity.EXTRA_DATA, getFilmById(filmId))

                startActivityForResult(this, START_DETAILS_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    private fun getFilmById(id: Int): FilmData {
        return when (id) {
            1 -> FilmData(
                1,
                resources.getString(R.string.film_1_name),
                resources.getString(R.string.film_1_description),
                R.drawable.poster_1
            )
            2 -> FilmData(
                2,
                resources.getString(R.string.film_2_name),
                resources.getString(R.string.film_2_description),
                R.drawable.poster_2
            )
            3 -> FilmData(
                3,
                resources.getString(R.string.film_3_name),
                resources.getString(R.string.film_3_description),
                R.drawable.poster_3
            )
            else -> throw Exception("Required parameter id was not provided")
        }
    }

}