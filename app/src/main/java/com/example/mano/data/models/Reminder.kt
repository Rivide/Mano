package com.example.mano.data.models

class Reminder(id: Long, entryId: Long, position: Long, var dateTime: Long)
    : Component(id, entryId, position, "reminder")