package ru.otus.otushometask1.data.database.repositories

import ru.otus.otushometask1.App
import ru.otus.otushometask1.data.database.dao.FilmDao
import ru.otus.otushometask1.data.database.entity.Film

object FilmsRepository {

    private lateinit var instance: FilmsRepository

    private val db by lazy {
        App().getInstance().getDatabase()
    }

    lateinit var filmDao: FilmDao

    fun getInstance(): FilmsRepository {
        instance = this

        filmDao = db.filmDao()

        return instance
    }

    fun selectAll(): List<Film> {
        return filmDao.selectAll()
    }

    fun getFavorites(): List<Film>{
        return  filmDao.selectAllFavorites()
    }

    fun insertFilms(films: List<Film>) {
        return filmDao.insertFilms(films)
    }

    fun makeFavorite(id: Int, isLiked: Boolean) {
        return filmDao.makeFavorite(id, isLiked)
    }

    fun makeFavorite(film: Film, isLiked: Boolean) {
        film.isLiked = isLiked
        return filmDao.makeFavorite(film)
    }

    fun selectById(id: Int): Film? {
        return filmDao.selectById(id)
    }

    fun likeMovie(movie: Film) {
        filmDao.insertFilm(movie)
    }

    fun delete(movie: Film) {
        filmDao.deleteFilm(movie)
    }

    fun clearFilmsTable() {
        filmDao.clearTable()
    }

    fun getFilmsCount(): Int {
        return filmDao.getFilmsCount()
    }
}