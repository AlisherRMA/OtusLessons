package ru.otus.otushometask1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        initBottomNavigation()
    }

    private fun initBottomNavigation(){
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)

        bottomNavigationView.selectedItemId = R.id.navigation_favorites
        bottomNavigationView.setOnNavigationItemSelectedListener {
            Log.d("ACT_ID", it.itemId.toString());
            when (it.itemId) {
                R.id.navigation_home -> {
                    Intent(this, MainActivity::class.java).apply {
                        startActivity(this)
                    }
                    true
                }
                R.id.navigation_favorites -> true
            }
            false
        }
    }
}