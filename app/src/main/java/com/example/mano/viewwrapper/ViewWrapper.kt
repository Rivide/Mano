package com.example.mano.viewwrapper

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

//TODO: investigate warning when ViewWrapper is object

class ViewWrapper {
    lateinit var view: View

    companion object {
        fun withParent(activity: Activity): (Int) -> ViewWrapper {
            val viewWrapper = ViewWrapper()

            return {
                viewWrapper.withView(activity.findViewById(it))
            }
        }
        fun withParent(view: View): (Int) -> ViewWrapper {
            val viewWrapper = ViewWrapper()

            return {
                viewWrapper.withView(view.findViewById(it))
            }
        }
    }

    private fun withView(viewToWrap: View): ViewWrapper {
        view = viewToWrap
        return this
    }

    var text: String
        get() = (view as TextView).text.toString()
        set(newText) { (view as TextView).text = newText }
}

