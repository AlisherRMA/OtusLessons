package ru.otus.otushometask1.data.dao

import androidx.room.*
import ru.otus.otushometask1.data.entity.Film

@Dao
interface FilmDao {
    @Query("SELECT * FROM films")
    fun selectAll(): List<Film>

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
}