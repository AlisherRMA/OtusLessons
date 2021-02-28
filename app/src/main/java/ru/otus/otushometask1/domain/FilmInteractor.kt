package ru.otus.otushometask1.domain



import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.otus.otushometask1.data.FilmService
import ru.otus.otushometask1.data.entity.Film
import ru.otus.otushometask1.data.entity.PageableResponse


class FilmInteractor(private val filmService: FilmService) {

    fun getFilms(callback: GetRepoCallback) {
        filmService.getFilms().enqueue(object : Callback<PageableResponse> {
            override fun onResponse(call: Call<PageableResponse>,
                                    response: Response<PageableResponse>) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body()!!.results)
                } else {
                    callback.onError(response.code().toString() + "")
                }
            }

            override fun onFailure(call: Call<PageableResponse>, t: Throwable) {
                callback.onError("Network error probably...")
            }
        })
    }

    interface GetRepoCallback {
        fun onSuccess(repos: List<Film>)
        fun onError(error: String)
    }
}
