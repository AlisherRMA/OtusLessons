package ru.otus.otushometask1

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.otus.otushometask1.data.database.dao.FilmDao
import ru.otus.otushometask1.data.database.entity.FavoriteFilms
import ru.otus.otushometask1.data.database.entity.Film

@Database(entities = [Film::class, FavoriteFilms::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}