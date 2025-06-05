package com.katyshevtseva.filmshelf.presentation.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.katyshevtseva.filmshelf.R

const val NULL_RATING_FOR_DISPLAY = 0.0

fun showAlertDialog(context: Context, title: String, message: String) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton("ОК") { dialog: DialogInterface, which: Int ->
        dialog.dismiss()
    }
    builder.create().show()
}

fun <T> setupSpinner(
    context: Context,
    list: List<T>,
    spinner: Spinner,
    onItemSelect: (T) -> Unit
) {

    val adapter = ArrayAdapter(context, R.layout.spinner_item, list)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter

    spinner.onItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = list[position]
                onItemSelect(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
}

fun getYearRangeString(start: Int, end: Int): String {
    return "$start - $end"
}