package com.katyshevtseva.filmshelf.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.katyshevtseva.filmshelf.R
import com.katyshevtseva.filmshelf.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.navigation_home -> HomeFragment()
                R.id.navigation_favorites -> FavoritesFragment()
                R.id.navigation_account -> AccountFragment()
                else -> throw RuntimeException("Unknown item id")
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, selectedFragment)
                .commit()

            true
        }
    }
}