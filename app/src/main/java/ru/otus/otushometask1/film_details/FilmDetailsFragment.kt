package ru.otus.otushometask1.film_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import ru.otus.otushometask1.data_classes.DetailsData
import ru.otus.otushometask1.data_classes.FilmData
import ru.otus.otushometask1.R

class FilmDetailsFragment : Fragment() {
    companion object {
        const val TAG = "FilmDetailsFragment"
        const val EXTRA_DATA = "EXTRA_DATA"

        fun newInstance(filmData: FilmData): FilmDetailsFragment {
            val args = Bundle()
            args.putParcelable(EXTRA_DATA, filmData)

            val fragment = FilmDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getParcelable<FilmData>(EXTRA_DATA).let {
            it?.name?.let { filmName -> initToolbar(view, filmName) }

            view.findViewById<ImageView>(R.id.imageView).setImageDrawable(it?.image?.let { imgRes ->
                getDrawable(
                    requireContext(),
                    imgRes
                )
            })
            view.findViewById<TextView>(R.id.textViewDesc).text = it?.description
        }
    }

    private fun initToolbar(view: View, toolbarTitle: String){
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = toolbarTitle

        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
            (activity as AppCompatActivity).supportActionBar?.show()
        }
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