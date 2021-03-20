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
    private val filmsLiveData = MutableLiveData<List<Film>>()
    private val errorLiveData = MutableLiveData<String>()

    private  val favoriteFilmsLiveData = MutableLiveData<List<Film>>()

    private val filmsInteractor = App.instance.filmInteractor


    val films: LiveData<List<Film>>
        get() = filmsLiveData

    val favoriteFilms: LiveData<List<Film>>
        get() = favoriteFilmsLiveData

    val error: LiveData<String>
        get() = errorLiveData

    fun getFilms(isInitial: Boolean) {
        filmsInteractor.getFilms(isInitial, object : FilmInteractor.GetRepoCallback {
            override fun onSuccess(repos: List<Film>) {
                filmsLiveData.postValue(repos)

            }

            override fun onError(error: String) {
                errorLiveData.postValue(error)
            }
        })
    }

    fun getFavoriteFilms(){
       val films = filmsInteractor.getFavorites()
        favoriteFilmsLiveData.postValue(films)
    }

    fun makeFavorite(film: Film, isLiked: Boolean){
        filmsInteractor.makeFavorite(film, isLiked)
        getFavoriteFilms()
    }

}
