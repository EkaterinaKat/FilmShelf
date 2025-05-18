package com.katyshevtseva.filmshelf.presentation.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

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