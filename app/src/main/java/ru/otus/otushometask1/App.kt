package ru.otus.otushometask1

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.otus.otushometask1.data.FilmService
import ru.otus.otushometask1.domain.FilmInteractor

class App : Application() {

    lateinit var filmService: FilmService
    lateinit var filmInteractor: FilmInteractor

    override fun onCreate() {
        super.onCreate()
        instance = this

        initRetrofit()
        initInteractor()
    }

    private fun initInteractor() {
        filmInteractor = FilmInteractor(filmService)
    }

    private fun initRetrofit() {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                return@addInterceptor chain.proceed(
                    chain
                        .request()
                        .newBuilder()
                        .build()
                )
            }
            .addInterceptor(HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        filmService = retrofit.create(FilmService::class.java)
    }

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"

        lateinit var instance: App
            private set
    }
}
