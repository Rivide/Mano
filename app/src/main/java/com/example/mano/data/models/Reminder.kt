package com.example.mano.data.models

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Reminder(id: Long, entryId: Long, position: Long, var dateTime: Long)
  : Component(id, entryId, position, "reminder") {
  override fun toString(): String {
    return Json.encodeToString(this)
  }
  }