package ru.otus.otushometask1.presentation.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.otus.otushometask1.App
import ru.otus.otushometask1.data.entity.Film
import ru.otus.otushometask1.data.repositories.PrefRepository
import ru.otus.otushometask1.domain.FilmInteractor

class FilmsListViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
//        private var currentPage = 0
    }

    private val filmsLiveData = MutableLiveData<List<Film>>()
    private val errorLiveData = MutableLiveData<String>()

    private val prefRepository by lazy { PrefRepository(getApplication()) }
//    private val selectedRepoUrlLiveData = MutableLiveData<String>()

    private val filmsInteractor = App.instance.filmInteractor


    val films: LiveData<List<Film>>
        get() = filmsLiveData

    val error: LiveData<String>
        get() = errorLiveData

    private var currentPage: Int
        get() = prefRepository.getLastRequestedPage()
        set(page) {
            prefRepository.setLastRequestedPage(page)
        }

//    val selectedRepoUrl: LiveData<String>
//        get() = selectedRepoUrlLiveData

    fun getFilms() {
        currentPage = 1
        val cachedFilmsSize = filmsInteractor.getFilmsSize()
        Toast.makeText(App.instance, cachedFilmsSize.toString(), Toast.LENGTH_SHORT).show()
        filmsInteractor.getFilms(currentPage, object : FilmInteractor.GetRepoCallback {
            override fun onSuccess(repos: List<Film>) {
                filmsLiveData.postValue(repos)
            }

            override fun onError(error: String) {
                errorLiveData.postValue(error)
            }
        })
    }

//    fun onRepoSelect(repoUrl: String) {
//        selectedRepoUrlLiveData.postValue(repoUrl)
//    }

}
