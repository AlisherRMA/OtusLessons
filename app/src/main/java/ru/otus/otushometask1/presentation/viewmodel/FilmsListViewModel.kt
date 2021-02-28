package ru.otus.otushometask1.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import ru.otus.otushometask1.App
import ru.otus.otushometask1.data.entity.Film
import ru.otus.otushometask1.domain.FilmInteractor

class FilmsListViewModel : ViewModel() {
    private val filmsLiveData = MutableLiveData<List<Film>>()
    private val errorLiveData = MutableLiveData<String>()
//    private val selectedRepoUrlLiveData = MutableLiveData<String>()

    private val filmsInteractor = App.instance!!.filmInteractor

    val films: LiveData<List<Film>>
        get() = filmsLiveData

    val error: LiveData<String>
        get() = errorLiveData

//    val selectedRepoUrl: LiveData<String>
//        get() = selectedRepoUrlLiveData

    fun onLoadData() {
        filmsInteractor.getFilms(object : FilmInteractor.GetRepoCallback {
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
