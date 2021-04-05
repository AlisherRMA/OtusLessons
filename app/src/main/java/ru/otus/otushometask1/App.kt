package ru.otus.otushometask1

import android.app.Application
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.otus.otushometask1.data.network.repositories.FilmService
import ru.otus.otushometask1.domain.FilmInteractor

class App : Application() {

    lateinit var filmService: FilmService
    lateinit var filmInteractor: FilmInteractor
    lateinit var db: AppDatabase

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"

        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initDB()
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

   private fun initDB(){
       db = Room.databaseBuilder(
           applicationContext,
           AppDatabase::class.java,
           "films"
       )
           .allowMainThreadQueries()
           .fallbackToDestructiveMigration()
           .build()
   }



    fun getInstance(): App {
        return instance
    }

    fun getDatabase(): AppDatabase {
        return db
    }
}
