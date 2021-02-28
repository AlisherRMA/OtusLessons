package ru.otus.otushometask1.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.otus.otushometask1.data.entity.Film
import ru.otus.otushometask1.data.entity.PageableResponse

interface FilmService {
    @GET("movie/top_rated?api_key=bc5b28bd60b94901618a6c5e273ccf49&language=en-US&page=1")
    fun getFilms(): Call<PageableResponse>

    @GET("films?id=1&name=blabla")
    fun getFilmById(@Query("image") id: String, @Query("name") name:String): Call<Film>
}


interface Asr {

}