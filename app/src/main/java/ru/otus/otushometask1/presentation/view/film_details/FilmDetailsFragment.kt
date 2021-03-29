package ru.otus.otushometask1.presentation.view.film_details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import ru.otus.otushometask1.data.network.dto.DetailsData
import ru.otus.otushometask1.R
import ru.otus.otushometask1.data.database.entity.Film
import ru.otus.otushometask1.presentation.view.films_list.FilmsVH

class FilmDetailsFragment : Fragment() {
    companion object {
        const val TAG = "FilmDetailsFragment"
        const val EXTRA_DATA = "EXTRA_DATA"

        fun newInstance(filmData: Film): FilmDetailsFragment {
            val args = Bundle()
            args.putParcelable(EXTRA_DATA, filmData)

            val fragment =
                FilmDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var image: ImageView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        arguments?.getParcelable<Film>(EXTRA_DATA).let {
            it?.title?.let { filmName -> initToolbar(view, filmName) }
            it?.image?.let {
                Glide.with(image.context)
                    .load("${FilmsVH.filmsUrl}${it}")
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


            view.findViewById<TextView>(R.id.textViewDesc).text = it?.overview
        }
    }

    private fun initToolbar(view: View, toolbarTitle: String) {
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = toolbarTitle

        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
            (activity as AppCompatActivity).supportActionBar?.show()
        }
    }

    private fun initViews(view: View) {
        image = view.findViewById(R.id.imageView)
        progressBar = view.findViewById(R.id.progress)
    }

    override fun onDestroyView() {
        sendDataBackToPreviousActivity()
        super.onDestroyView()
    }

    private fun sendDataBackToPreviousActivity() {
        lateinit var sendBackData: DetailsData
        view?.let {
            sendBackData = DetailsData(
                it.findViewById<CheckBox>(R.id.checkBox).isChecked,
                it.findViewById<EditText>(R.id.editText)?.text.toString()
            )
        }
        (activity as? DetailsClickListener)?.onDetailsFragmentFinished(sendBackData)
    }

    interface DetailsClickListener {
        fun onDetailsFragmentFinished(filmItem: DetailsData)
    }


}