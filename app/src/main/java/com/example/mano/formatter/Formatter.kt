package com.example.mano.formatter

import com.example.mano.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object Formatter {
  val dateFormat = DateTimeFormatter.ofPattern("MM/dd/yy")
  val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

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
  fun getDate(epochSecond: Long): String {
    return getDate(LocalDateTime.ofEpochSecond(epochSecond, 0,
      OffsetDateTime.now().offset))
  }
  fun getTime(epochSecond: Long): String {
    return getTime(LocalDateTime.ofEpochSecond(epochSecond, 0,
      OffsetDateTime.now().offset))
  }
  fun parseDate(text: CharSequence): LocalDate {
    return LocalDate.parse(text, dateFormat)
  }
  fun parseTime(text: CharSequence): LocalTime {
    return LocalTime.parse(text, timeFormat)
  }
}