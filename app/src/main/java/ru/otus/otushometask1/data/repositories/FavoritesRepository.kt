package ru.otus.otushometask1.data.repositories

import android.widget.Toast
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