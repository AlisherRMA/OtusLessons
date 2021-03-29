package ru.otus.otushometask1.data.network.repositories

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.otus.otushometask1.data.network.dto.FilmDto
import ru.otus.otushometask1.data.network.dto.PageableResponse

interface FilmService {
    companion object {
        const val API_KEY = "bc5b28bd60b94901618a6c5e273ccf49"
        const val DEFAULT_LANGUAGE = "en-US"
    }
    @GET("movie/top_rated")
    fun getFilms(@Query("api_key") apiKey: String = API_KEY,
                 @Query("page") page: Int,
                 @Query("language") language: String = DEFAULT_LANGUAGE
    ): Call<PageableResponse>

    @GET("films?id=1&name=blabla")
    fun getFilmById(@Query("image") id: String, @Query("name") name:String): Call<FilmDto>
}


interface Asr {

}