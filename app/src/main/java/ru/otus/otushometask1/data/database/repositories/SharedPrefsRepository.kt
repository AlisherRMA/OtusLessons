package ru.otus.otushometask1.data.database.repositories

import android.content.Context
import android.content.SharedPreferences
import ru.otus.otushometask1.data.PREFERENCE_NAME
import ru.otus.otushometask1.data.PREF_LAST_REQUESTED_PAGE

class PrefRepository(val context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    private fun String.put(long: Long) {
        editor.putLong(this, long)
        editor.commit()
    }

    private fun String.put(int: Int) {
        editor.putInt(this, int)
        editor.commit()
    }

    private fun String.put(string: String) {
        editor.putString(this, string)
        editor.commit()
    }

    private fun String.put(boolean: Boolean) {
        editor.putBoolean(this, boolean)
        editor.commit()
    }

    private fun String.getLong() = pref.getLong(this, 0)

    private fun String.getInt() = pref.getInt(this, 0)

    private fun String.getString() = pref.getString(this, "")!!

    private fun String.getBoolean() = pref.getBoolean(this, false)

    fun setLastRequestedPage(date: Int) {
        PREF_LAST_REQUESTED_PAGE.put(date)
    }

    fun getLastRequestedPage() = PREF_LAST_REQUESTED_PAGE.getInt()
}