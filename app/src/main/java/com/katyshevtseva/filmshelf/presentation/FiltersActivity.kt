package com.katyshevtseva.filmshelf.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.katyshevtseva.filmshelf.FilmShelfApp
import com.katyshevtseva.filmshelf.R
import com.katyshevtseva.filmshelf.databinding.ActivityFiltersBinding
import com.katyshevtseva.filmshelf.domain.model.Country
import com.katyshevtseva.filmshelf.domain.model.Genre
import com.katyshevtseva.filmshelf.domain.model.RatingCategory
import com.katyshevtseva.filmshelf.presentation.util.SpinnerAdapter
import com.katyshevtseva.filmshelf.presentation.util.YearSliderData
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

    private lateinit var genreSpinnerAdapter: SpinnerAdapter<Genre>
    private lateinit var countrySpinnerAdapter: SpinnerAdapter<Country>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        component.inject(this)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.genresLD.observe(this) {
            genreSpinnerAdapter = SpinnerAdapter<Genre>(binding.genreSpinner, it)
            genreSpinnerAdapter.setupSpinner(this)
            genreSpinnerAdapter.setOnItemSelect {
                viewModel.onGenreSelect(it)
            }
        }
        viewModel.countriesLD.observe(this) {
            countrySpinnerAdapter = SpinnerAdapter<Country>(binding.countrySpinner, it)
            countrySpinnerAdapter.setupSpinner(this)
            countrySpinnerAdapter.setOnItemSelect {
                viewModel.onCountrySelect(it)
            }
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
        viewModel.yearSliderDataLD.observe(this) {
            setupYearSlider(it)
        }
        viewModel.selectedYearRangeStringLD.observe(this) {
            binding.yearTextView.text = it
        }
        viewModel.initRatingLD.observe(this) {
            setupRatingSelect(it)
        }
        viewModel.applyButtonTextLD.observe(this) {
            binding.applyButton.text = it
        }
    }

    private fun setupYearSlider(data: YearSliderData) {
        val floatStart = data.entireRange.start.toFloat()
        val floatEnd = data.entireRange.end.toFloat()

        binding.yearSlider.valueFrom = floatStart
        binding.yearSlider.valueTo = floatEnd
        binding.yearSlider.values = listOf(floatStart, floatEnd)

        viewModel.onYearRangeSelect(data.entireRange.start, data.entireRange.end)

        binding.yearSlider.addOnChangeListener { slider, value, fromUser ->
            val values = slider.values
            viewModel.onYearRangeSelect(values[0].toInt(), values[1].toInt())
        }

        if (data.initialRange != null) {
            binding.yearSlider.values = listOf(
                data.initialRange.start.toFloat(),
                data.initialRange.end.toFloat()
            )
        }
    }

    private fun setupRatingSelect(initialValue: RatingCategory) {
        for (category in RatingCategory.entries) {
            val radioButton = RadioButton(this)
            radioButton.text = getRatingCategoryString(category)
            radioButton.id = category.id
            binding.ratingGroup.addView(radioButton)
            if (category == initialValue) {
                radioButton.isChecked = true
            }
        }
        binding.ratingGroup.setOnCheckedChangeListener { group, checkedId ->
            viewModel.onRatingCategorySelect(RatingCategory.getById(checkedId))
        }
    }

    private fun getRatingCategoryString(ratingCategory: RatingCategory): String {
        return when (ratingCategory) {
            RatingCategory.BEST -> getString(R.string.best_rating, ratingCategory.numRepresentation)
            RatingCategory.GOOD -> getString(R.string.good_rating, ratingCategory.numRepresentation)
            RatingCategory.ALL -> getString(R.string.all)
        }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, FiltersActivity::class.java)
        }
    }
}