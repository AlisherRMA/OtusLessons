package ru.otus.otushometask1.data.repositories

import ru.otus.otushometask1.App
import ru.otus.otushometask1.data.dao.FilmDao
import ru.otus.otushometask1.data.entity.Film

object FavoritesRepository {

    private lateinit var instance: FavoritesRepository

    private val db by lazy {
        App().getInstance().getDatabase()
    }

    lateinit var filmDao: FilmDao

    fun getInstance(): FavoritesRepository {
        instance = this

        filmDao = db.filmDao()

        return instance
    }

    fun selectAll(): List<Film> {
        return filmDao.selectAll()
    }

    fun insertFilms(films: List<Film>) {
        return  filmDao.insertFilms(films)
    }

    fun selectById(id: Int): Film? {
        return filmDao.selectById(id)
    }

    fun likeMovie(movie: Film){
        filmDao.insertFilm(movie)
    }

    fun delete(movie: Film) {
        filmDao.deleteFilm(movie)
    }

    fun clearFilmsTable(){
        filmDao.clearTable()
    }

    fun getFilmsCount(): Int {
        return filmDao.getFilmsCount()
    }
}