package com.example.mano.fragments

import android.os.Build
import android.view.MotionEvent
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.example.mano.formatter.Formatter
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class DateTimePicker(fragmentManager: FragmentManager,
                     val dateView: TextView, val timeView: TextView,
                     var dateTime: LocalDateTime = LocalDateTime.now()) {
  init {
    dateView.text = Formatter.getDate(dateTime) //dateTime.format(DateTimeFormatter.ofPattern("MM/dd/yy"))
    timeView.text = Formatter.getTime(dateTime) //dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

    val datePickerFragment = DatePickerFragment(dateTime.toLocalDate()) {
      dateTime = LocalDateTime.of(date, dateTime.toLocalTime())
      dateView.text = Formatter.getDate(date) //date.format(DateTimeFormatter.ofPattern("MM/dd/yy"))
    }
    val timePickerFragment = TimePickerFragment(dateTime.toLocalTime()) {
      dateTime = LocalDateTime.of(dateTime.toLocalDate(), time)
      timeView.text = Formatter.getTime(time) //time.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    dateView.setOnTouchListener {
        view, motionEvent ->
      if (motionEvent.action == MotionEvent.ACTION_DOWN) {
        datePickerFragment.requestOpen(fragmentManager)
        return@setOnTouchListener true
      }
      return@setOnTouchListener false
    }

    timeView.setOnTouchListener {
        view, motionEvent ->
      if (motionEvent.action == MotionEvent.ACTION_DOWN) {
        timePickerFragment.requestOpen(fragmentManager)
        return@setOnTouchListener true
      }
      return@setOnTouchListener false
    }
  }
}