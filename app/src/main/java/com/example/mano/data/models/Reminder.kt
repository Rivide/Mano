package com.example.mano.data.models

import java.util.*

class Reminder(id: Long, entryId: Long, position: Long, val dateTime: Long)
    : Component(id, entryId, position, "reminder")