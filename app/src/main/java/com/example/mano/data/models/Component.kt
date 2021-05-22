package com.example.mano.data.models

interface Component {
  var id: Long
  var entryId: Long
  var position: Long
  val type: String
}