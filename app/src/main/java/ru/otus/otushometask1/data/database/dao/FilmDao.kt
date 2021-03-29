package ru.otus.otushometask1.data.database.dao

import androidx.room.*
import ru.otus.otushometask1.data.database.entity.Film

@Dao
interface FilmDao {
    @Query("SELECT * FROM films ORDER BY createDate")
    fun selectAll(): List<Film>

    @Query("select * from films where is_liked=1")
    fun selectAllFavorites(): List<Film>

    @Query("SELECT * FROM films WHERE id=:id")
    fun selectById(id: Int): Film?

    @Delete
    fun deleteFilm(movie: Film)

    @Insert
    fun insertFilm(film: Film)

    @Query("UPDATE films SET is_liked = :isLiked WHERE id = :id")
    fun makeFavorite(id: Int, isLiked: Boolean)

    @Update
    fun makeFavorite(film: Film)

    @Query("DELETE FROM films")
    fun clearTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilms(films: List<Film>)

    @Query("SELECT count(*) FROM films")
    fun getFilmsCount(): Int

//    @Query("SELECT count(*) FROM favorite_films")
//    fun getFavoritesCount(): Int
}