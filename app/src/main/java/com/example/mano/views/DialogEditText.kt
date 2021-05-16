package com.example.mano.views

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentManager

abstract class DialogEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    private val gestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent?) = true
        })

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event).let {
            if (it) {
                if (context is AppCompatActivity) {
                    requestDialog((context as AppCompatActivity).supportFragmentManager)
                }
                true
            } else false
        }
    }

    abstract fun requestDialog(fragmentManager: FragmentManager)
}