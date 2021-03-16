package ru.otus.otushometask1.data.dao

import androidx.room.*
import ru.otus.otushometask1.data.entity.FavoriteFilms
import ru.otus.otushometask1.data.entity.Film

@Dao
interface FilmDao {
    @Query("SELECT * FROM films ORDER BY createDate")
    fun selectAll(): List<Film>

    @Query("SELECT * from favorite_films")
    fun selectAllFavorites(): List<FavoriteFilms>

    @Query("SELECT * FROM films WHERE id=:id")
    fun selectById(id: Int): Film?

    @Delete
    fun deleteFilm(movie: Film)

    @Insert
    fun insertFilm(film: Film)

    @Query("DELETE FROM films")
    fun clearTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilms(films: List<Film>)

    @Query("SELECT count(*) FROM films")
    fun getFilmsCount(): Int

    @Query("SELECT count(*) FROM favorite_films")
    fun getFavoritesCount(): Int
}