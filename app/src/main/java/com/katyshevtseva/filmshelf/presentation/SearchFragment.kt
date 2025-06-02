package com.katyshevtseva.filmshelf.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.katyshevtseva.filmshelf.FilmShelfApp
import com.katyshevtseva.filmshelf.R
import com.katyshevtseva.filmshelf.databinding.FragmentSearchBinding
import com.katyshevtseva.filmshelf.presentation.adapter.MovieAdapter
import com.katyshevtseva.filmshelf.presentation.util.showAlertDialog
import com.katyshevtseva.filmshelf.presentation.viewmodel.SearchViewModel
import com.katyshevtseva.filmshelf.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class SearchFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as FilmShelfApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchBinding is null")

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
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        setupEditTextListener()
        observeViewModel()
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

    private fun setupEditTextListener() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onSearchInput(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}