package ru.otus.otushometask1.film_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import ru.otus.otushometask1.DetailsData
import ru.otus.otushometask1.FilmData
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
            view.findViewById<TextView>(R.id.textView).text = it?.name
            view.findViewById<ImageView>(R.id.imageView).setImageDrawable(it?.image?.let { it1 ->
                getDrawable(
                    requireContext(),
                    it1
                )
            })
            view.findViewById<TextView>(R.id.textViewDesc).text = it?.description
        }
    }

    override fun onDestroyView() {
        sendDataBackToPreviousActivity()
        super.onDestroyView()
    }

    private fun sendDataBackToPreviousActivity() {
        lateinit var sendBackData: DetailsData;
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