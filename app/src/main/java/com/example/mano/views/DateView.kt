package com.example.mano.views

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentManager
import com.example.mano.formatter.Formatter
import com.example.mano.fragments.DatePickerFragment
import java.time.LocalDate

class DateView(context: Context, attrs: AttributeSet) : DialogEditText(context, attrs) {

    private val datePickerFragment = DatePickerFragment(
        onUpdate = {
            setText(Formatter.getDate(date))
        },
        onCreate = {
            date = Formatter.parseDate(text.toString())
        }
    )

    init {
        setText(Formatter.getDate(LocalDate.now()))
    }

    override fun requestDialog(fragmentManager: FragmentManager) {
        datePickerFragment.requestOpen(fragmentManager)
    }
}