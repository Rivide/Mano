package com.example.mano.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.time.LocalDate
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
open class DatePickerFragment(var date: LocalDate = LocalDate.now(),
                              val onUpdate: DatePickerFragment.() -> Unit = {},
                              val onCreate: DatePickerFragment.() -> Unit = {})
  : DialogFragment(), DatePickerDialog.OnDateSetListener {

  var hidden = true

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    hidden = false
    onCreate()

    // android months are 0-11
    return DatePickerDialog(activity!!, this,
      date.year, date.monthValue - 1, date.dayOfMonth)
  }

  override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
    // android months are 0-11
    date = LocalDate.of(year, month + 1, dayOfMonth)
    onUpdate()
  }

  override fun onDismiss(dialog: DialogInterface) {
    super.onDismiss(dialog)
    hidden = true
  }

  fun requestOpen(fragmentManager: FragmentManager) {
    if (hidden) {
      show(fragmentManager, "datePicker")
    }
  }
}