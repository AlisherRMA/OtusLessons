package ru.otus.otushometask1

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.otus.otushometask1.data.dao.FilmDao
import ru.otus.otushometask1.data.entity.FavoriteFilms
import ru.otus.otushometask1.data.entity.Film

@Database(entities = [Film::class, FavoriteFilms::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}