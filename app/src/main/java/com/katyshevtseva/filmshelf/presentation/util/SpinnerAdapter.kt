package com.katyshevtseva.filmshelf.presentation.util

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.katyshevtseva.filmshelf.R

class SpinnerAdapter<T>(
    private val spinner: Spinner,
    private val data: SpinnerData<T>
) {

    fun setupSpinner(context: Context) {
        val adapter = ArrayAdapter(context, R.layout.spinner_item, data.items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        if (data.initItem != null) {
            selectItem(data.initItem)
        }
    }

    fun setOnItemSelect(onItemSelect: (T) -> Unit) {
        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = data.items[position]
                    onItemSelect(selectedItem)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
    }

    fun selectItem(t: T) {
        val position: Int = data.items.indexOf(t)
        if (position >= 0) {
            spinner.setSelection(position)
        } else {
            throw RuntimeException("Spinner item not found")
        }
    }
}