package com.katyshevtseva.filmshelf.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.katyshevtseva.filmshelf.FilmShelfApp
import com.katyshevtseva.filmshelf.databinding.FragmentFavoritesBinding
import com.katyshevtseva.filmshelf.presentation.adapter.MovieAdapter
import com.katyshevtseva.filmshelf.presentation.viewmodel.FavoritesViewModel
import com.katyshevtseva.filmshelf.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as FilmShelfApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavoritesViewModel::class.java]
    }

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding
        get() = _binding ?: throw RuntimeException("FragmentFavoritesBinding is null")

    private val movieAdapter = MovieAdapter()

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadMovies()
    }

    private fun setupRecycleView() {
        binding.moviesRecyclerView.setLayoutManager(
            GridLayoutManager(
                requireActivity(),
                2
            )
        )
        movieAdapter.onMovieClickListener = { movie ->
            startActivity(MovieDetailsActivity.newIntent(requireActivity(), movie))
        }
        binding.moviesRecyclerView.setAdapter(movieAdapter)
    }

    private fun observeViewModel() {
        viewModel.moviesLD.observe(viewLifecycleOwner) {
            movieAdapter.submitList(it)
        }
    }
}