package com.katyshevtseva.filmshelf.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.katyshevtseva.filmshelf.FilmShelfApp
import com.katyshevtseva.filmshelf.R
import com.katyshevtseva.filmshelf.databinding.ActivityMovieDetailsBinding
import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.presentation.adapter.TrailerAdapter
import com.katyshevtseva.filmshelf.presentation.util.showAlertDialog
import com.katyshevtseva.filmshelf.presentation.viewmodel.MovieDetailsViewModel
import java.lang.String
import javax.inject.Inject
import kotlin.getValue
import kotlin.lazy

class MovieDetailsActivity : AppCompatActivity() {

    private val component by lazy {
        (application as FilmShelfApp).component
    }

    @Inject
    lateinit var viewModelFactory: MovieDetailsViewModel.Factory

    private lateinit var viewModel: MovieDetailsViewModel

    private val binding by lazy {
        ActivityMovieDetailsBinding.inflate(layoutInflater)
    }

    private val trailerAdapter = TrailerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        component.inject(this)

        val movieId = intent.getIntExtra(MOVIE_ID_KEY, UNRESOLVED_MOVIE_ID)
        if (movieId == UNRESOLVED_MOVIE_ID) {
            showAlertDialog(this, resources.getString(R.string.error), "Ашипка")
        }

        viewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return viewModelFactory.create(movieId) as T
                }
            }
        )[MovieDetailsViewModel::class.java]

        trailerAdapter.onTrailerClickListener = { trailer ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(trailer.url?.toUri())
            startActivity(intent)
        }
        binding.trailerRecyclerView.setLayoutManager(LinearLayoutManager(this))
        binding.trailerRecyclerView.setAdapter(trailerAdapter)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.movieLD.observe(this) { movie ->
            Glide.with(this).load(movie.posterUrl).into(binding.posterImageView)
            binding.yearTextView.text = String.valueOf(movie.year)
            binding.titleTextView.text = movie.name
            binding.descTextView.text = movie.description
        }
        viewModel.errorLD.observe(this) {
            showAlertDialog(this, resources.getString(R.string.error), it)
        }
        viewModel.trailerLD.observe(this) {
            trailerAdapter.trailers = it
        }
    }

    companion object {

        const val MOVIE_ID_KEY = "MOVIE_ID_KEY"
        const val UNRESOLVED_MOVIE_ID = -1

        fun newIntent(context: Context, movie: Movie): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID_KEY, movie.id)
            return intent
        }
    }
}