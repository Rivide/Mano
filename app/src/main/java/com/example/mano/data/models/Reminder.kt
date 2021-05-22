package com.example.mano.data.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class Reminder(override var id: Long, override var entryId: Long, override var position: Long,
               var dateTime: Long): Component{

  override val type: String = "reminder"

  override fun toString(): String {
    return Json.encodeToString(this)
  }
}