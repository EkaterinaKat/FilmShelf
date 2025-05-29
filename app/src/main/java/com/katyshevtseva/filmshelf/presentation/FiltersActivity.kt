package com.katyshevtseva.filmshelf.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.katyshevtseva.filmshelf.FilmShelfApp
import com.katyshevtseva.filmshelf.R
import com.katyshevtseva.filmshelf.databinding.ActivityFiltersBinding
import com.katyshevtseva.filmshelf.presentation.util.setupSpinner
import com.katyshevtseva.filmshelf.presentation.util.showAlertDialog
import com.katyshevtseva.filmshelf.presentation.viewmodel.FiltersViewModel
import com.katyshevtseva.filmshelf.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class FiltersActivity : AppCompatActivity() {

    private val component by lazy {
        (application as FilmShelfApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FiltersViewModel::class.java]
    }

    private val binding by lazy {
        ActivityFiltersBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        component.inject(this)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.genresLD.observe(this) {
            setupSpinner(this, it, binding.genreSpinner) { viewModel.onGenreSelect(it) }
        }
        viewModel.countriesLD.observe(this) {
            setupSpinner(this, it, binding.countrySpinner) { viewModel.onCountrySelect(it) }
        }
        viewModel.errorLD.observe(this) {
            showAlertDialog(this, resources.getString(R.string.error), it)
        }
        viewModel.loadingLD.observe(this) {
            if (it)
                binding.loadingProgressBar.visibility = View.VISIBLE
            else
                binding.loadingProgressBar.visibility = View.GONE
        }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, FiltersActivity::class.java)
        }
    }
}