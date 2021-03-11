package ru.otus.otushometask1.domain


import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.otus.otushometask1.App
import ru.otus.otushometask1.MainActivity
import ru.otus.otushometask1.data.FilmService
import ru.otus.otushometask1.data.entity.Film
import ru.otus.otushometask1.data.entity.PageableResponse
import ru.otus.otushometask1.data.repositories.FavoritesRepository
import kotlin.coroutines.coroutineContext


class FilmInteractor(private val filmService: FilmService) {

    private val favoritesRepository by lazy {
        FavoritesRepository.getInstance()
    }

    fun getFilms(page: Int, callback: GetRepoCallback) {
        favoritesRepository.clearFilmsTable()
        val cachedFilmsSize = getFilmsSize()
        if (cachedFilmsSize > 0) {
            callback.onSuccess(favoritesRepository.selectAll())
        } else {
            filmService.getFilms(page = page).enqueue(object : Callback<PageableResponse> {
                override fun onResponse(
                    call: Call<PageableResponse>,
                    response: Response<PageableResponse>
                ) {
                    if (response.isSuccessful) {
                        favoritesRepository.insertFilms(response.body()!!.results)
                        callback.onSuccess(favoritesRepository.selectAll())
                    } else {
                        callback.onError(response.code().toString() + "")
                    }
                }

                override fun onFailure(call: Call<PageableResponse>, t: Throwable) {
                    callback.onError("Network error probably...")
                }
            })
        }
    }

    fun getFilmsSize(): Int {
        return favoritesRepository.getFilmsCount()
    }

    interface GetRepoCallback {
        fun onSuccess(repos: List<Film>)
        fun onError(error: String)
    }
}
