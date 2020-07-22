package com.example.mano.formatter

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object Formatter {
  private val dateFormat = DateTimeFormatter.ofPattern("MM/dd/yy")
  private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

  fun getDate(date: LocalDate): String {
    return date.format(dateFormat)
  }
  fun getTime(time: LocalTime): String {
    return time.format(timeFormat)
  }
  fun getDate(dateTime: LocalDateTime): String {
    return getDate(dateTime.toLocalDate())
  }
  fun getTime(dateTime: LocalDateTime): String {
    return getTime(dateTime.toLocalTime())
  }
}