package ru.otus.otushometask1.presentation.viewmodel

import android.R
import android.R.id.message
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.otus.otushometask1.App
import ru.otus.otushometask1.data.database.entity.Film
import ru.otus.otushometask1.domain.FilmInteractor
import ru.otus.otushometask1.domain.NotificationPublisher
import ru.otus.otushometask1.presentation.view.MainActivity


class FilmsListViewModel(application: Application) : AndroidViewModel(application) {
    val NOTIFICATION_CHANNEL_ID = "10001"
    private val default_notification_channel_id = "default"
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun makeFavorite(film: Film, isLiked: Boolean) {
//        filmsInteractor.makeFavorite(film, isLiked)
//        getFavoriteFilms()

        getNotification(film.title)?.let {
            scheduleNotification(it, 10000)
        }

//        getNotification(film.title)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scheduleNotification(notification: Notification, delay: Int) {
        val notificationIntent = Intent(App.instance, NotificationPublisher::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification)
        notificationIntent.putExtra("KEY", "YOUR VAL")
        val pendingIntent = PendingIntent.getBroadcast(
            App.instance,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager =
            App.instance.applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis] = pendingIntent
    }

    private fun getNotification(content: String): Notification {
        val builder =
            NotificationCompat.Builder(App.instance, default_notification_channel_id )
        builder.setContentTitle("Вы хотели посмотреть $content")
        builder.setContentText(content)
        builder.setSmallIcon(R.drawable.alert_light_frame)
        builder.setAutoCancel(true)
        builder.setChannelId(NOTIFICATION_CHANNEL_ID)
//        builder.setContentIntent(
//            PendingIntent.getBroadcast(
//                App.instance,
//                0,
//                notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//            ))
        return builder.build()
    }

}
