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
class DatePickerFragment(var date: LocalDate = LocalDate.now(),
                         val onUpdate: DatePickerFragment.() -> Unit = {})
  : DialogFragment(), DatePickerDialog.OnDateSetListener {

  var hidden = true

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    hidden = false

    return DatePickerDialog(activity!!, this, date.year, date.monthValue, date.dayOfMonth)
  }

  override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
    date = LocalDate.of(year, month, dayOfMonth)
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