package ru.otus.otushometask1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class FilmDetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
        const val EXTRA_SEND_BACK_DATA = "EXTRA_SEND_BACK_DATA"
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_details)


        intent.getParcelableExtra<FilmData>(EXTRA_DATA)?.let {
            findViewById<TextView>(R.id.textView).text = it.name
            findViewById<ImageView>(R.id.imageView).setImageDrawable(getDrawable(it.image))
            findViewById<TextView>(R.id.textViewDesc).text = it.description
        }

    }

    override fun onBackPressed() {
        sendDataBackToPreviousActivity()
        super.onBackPressed()
    }

    private fun sendDataBackToPreviousActivity() {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(
                EXTRA_SEND_BACK_DATA, DetailsData(
                    findViewById<CheckBox>(R.id.checkBox).isChecked,
                    findViewById<EditText>(R.id.editText).text.toString()
                )
            )
        })

        finish()
    }
}