package com.example.mano.viewwrapper

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.lang.reflect.Type

object ViewWrapper {
  var activity: Activity? = null
  set(value) {
    field = value
    if (value !== null) {
      viewGroup = null
    }
  }
  var viewGroup: ViewGroup? = null
  set(value) {
    field = value
    if (value !== null) {
      activity = null
    }
  }

  class Wrapper(viewID: Int) {
    var view: View = activity?.findViewById(viewID) ?: viewGroup!!.findViewById(viewID)

    var text: String
      get() = (view as TextView).text.toString()
      set(newText) { (view as TextView).text = newText }
  }

  fun getWrapper(): (Int) -> Wrapper {
    return { Wrapper(it) }
  }
}