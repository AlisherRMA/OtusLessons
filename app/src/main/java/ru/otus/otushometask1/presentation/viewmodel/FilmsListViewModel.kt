package ru.otus.otushometask1.presentation.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.otus.otushometask1.App
import ru.otus.otushometask1.data.database.entity.Film
import ru.otus.otushometask1.domain.FilmInteractor
import ru.otus.otushometask1.domain.NotificationPublisher
import java.util.*


class FilmsListViewModel(application: Application) : AndroidViewModel(application) {
    private val filmsLiveData = MutableLiveData<List<Film>>()
    private val errorLiveData = MutableLiveData<String>()

    private val favoriteFilmsLiveData = MutableLiveData<List<Film>>()

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

    fun getFavoriteFilms() {
        val films = filmsInteractor.getFavorites()
        favoriteFilmsLiveData.postValue(films)
    }

    fun makeFavorite(film: Film, isLiked: Boolean) {
        filmsInteractor.makeFavorite(film, isLiked)
        getFavoriteFilms()
    }

    fun watchLater(film: Film, notifyTime: Calendar){
        scheduleNotification(film, notifyTime)
    }

    private fun scheduleNotification(film: Film, notifyTime: Calendar) {
        val notificationIntent = Intent(App.instance, NotificationPublisher::class.java)
        val bundle = Bundle()
        bundle.putParcelable("film", film)
        notificationIntent.putExtra("filmBundle", bundle)
        val pendingIntent = PendingIntent.getBroadcast(
            App.instance,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val futureInMillis = notifyTime.timeInMillis
        Log.d("TIME", futureInMillis.toString())
        val alarmManager =
            App.instance.applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, futureInMillis] = pendingIntent
    }

    fun getFilmById(id: Int): Film? {
        return filmsInteractor.getFilmById(id)
    }

}
