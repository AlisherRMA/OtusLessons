package ru.otus.otushometask1.domain


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.otus.otushometask1.App
import ru.otus.otushometask1.data.network.repositories.FilmService
import ru.otus.otushometask1.data.database.entity.Film
import ru.otus.otushometask1.data.database.repositories.FilmsRepository
import ru.otus.otushometask1.data.database.repositories.PrefRepository
import ru.otus.otushometask1.data.network.dto.PageableResponse


class FilmInteractor(private val filmService: FilmService) {

    private val favoritesRepository by lazy {
        FilmsRepository.getInstance()
    }

    private val prefRepository by lazy {
        PrefRepository(
            App.instance
        )
    }

    private var currentPage: Int
        get() = prefRepository.getLastRequestedPage()
        set(page) {
            prefRepository.setLastRequestedPage(page)
        }

    fun getFilms(isInitial: Boolean, callback: GetRepoCallback) {
        val cachedFilmsSize = getFilmsSize()
        if(isInitial && cachedFilmsSize == 0) currentPage = 1
        if(!isInitial) currentPage++
        if (cachedFilmsSize > 0 && cachedFilmsSize/20 >= currentPage) {
            // if the requested page is already in the cache, return movies from the cache
            callback.onSuccess(favoritesRepository.selectAll())
        } else {
            // otherwise load films
            filmService.getFilms(page = currentPage).enqueue(object : Callback<PageableResponse> {
                override fun onResponse(
                    call: Call<PageableResponse>,
                    response: Response<PageableResponse>
                ) {
                    if (response.isSuccessful) {
                        val films = response.body()!!.results
                        favoritesRepository.insertFilms(films)
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

    fun getFavorites(): List<Film> {
        return favoritesRepository.getFavorites()
    }

    fun makeFavorite(film: Film, isLiked: Boolean){
       return favoritesRepository.makeFavorite(film, isLiked)
    }

    fun getFilmsSize(): Int {
        return favoritesRepository.getFilmsCount()
    }

    interface GetRepoCallback {
        fun onSuccess(repos: List<Film>)
        fun onError(error: String)
    }
}
