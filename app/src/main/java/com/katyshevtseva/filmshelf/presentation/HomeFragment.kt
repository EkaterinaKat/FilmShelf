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
import com.katyshevtseva.filmshelf.R
import com.katyshevtseva.filmshelf.databinding.FragmentHomeBinding
import com.katyshevtseva.filmshelf.domain.model.SortType
import com.katyshevtseva.filmshelf.presentation.adapter.MovieAdapter
import com.katyshevtseva.filmshelf.presentation.util.setupSpinner
import com.katyshevtseva.filmshelf.presentation.util.showAlertDialog
import com.katyshevtseva.filmshelf.presentation.viewmodel.HomeViewModel
import com.katyshevtseva.filmshelf.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject


class HomeFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as FilmShelfApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    private val movieAdapter = MovieAdapter()

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        observeViewModel()
        setupSortMenu()
        binding.filterButton.setOnClickListener {
            startActivity(FiltersActivity.newIntent(requireActivity()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        movieAdapter.onReachEndListener = { viewModel.loadNextPage() }
        binding.moviesRecyclerView.setAdapter(movieAdapter)
    }

    private fun observeViewModel() {
        viewModel.moviesLD.observe(viewLifecycleOwner) {
            movieAdapter.submitList(it)
        }
        viewModel.errorLD.observe(viewLifecycleOwner) {
            showAlertDialog(requireActivity(), resources.getString(R.string.error), it)
        }
        viewModel.loadingLD.observe(viewLifecycleOwner) {
            if (it)
                binding.loadingProgressBar.visibility = View.VISIBLE
            else
                binding.loadingProgressBar.visibility = View.GONE
        }
    }

    private fun setupSortMenu() {
        val sortTypes = listOf(
            SortTypeWrapper(SortType.POPULAR_FIRST, resources.getString(R.string.popular)),
            SortTypeWrapper(SortType.HIGH_RATING_FIRST, resources.getString(R.string.high_rating)),
            SortTypeWrapper(SortType.NEW_FIRST, resources.getString(R.string.new_)),
            SortTypeWrapper(SortType.OLD_FIRST, resources.getString(R.string.old))
        )

        setupSpinner(requireActivity(), sortTypes, binding.sortTypeSpinner) {
            viewModel.onSortTypeSelect(it.sortType)
        }
    }

    private class SortTypeWrapper(
        val sortType: SortType,
        val title: String
    ) {
        override fun toString() = title
    }
}