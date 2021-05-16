package com.example.mano.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
class TimePickerFragment(var time: LocalTime = LocalTime.now(),
                         val onUpdate: TimePickerFragment.() -> Unit = {},
                         val onCreate: TimePickerFragment.() -> Unit = {})
  : DialogFragment(), TimePickerDialog.OnTimeSetListener {

  var hidden = true

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    hidden = false
    onCreate()

    return TimePickerDialog(activity, this,
      time.hour, time.minute, DateFormat.is24HourFormat(activity)
    )
  }

  override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
    time = LocalTime.of(hour, minute)
    onUpdate()
  }

  override fun onDismiss(dialog: DialogInterface) {
    super.onDismiss(dialog)
    hidden = true
  }
  fun requestOpen(fragmentManager: FragmentManager) {
    if (hidden) {
      show(fragmentManager, "timePicker")
    }
  }
}
