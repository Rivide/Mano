package com.example.mano.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.mano.formatter.Formatter
import com.example.mano.fragments.TimePickerFragment
import java.time.LocalTime

class TimeView(context: Context, attrs: AttributeSet) : DialogEditText(context, attrs) {

    private val timePickerFragment = TimePickerFragment(
        onUpdate = {
            setText(Formatter.getTime(time))
        },
        onCreate = {
            time = Formatter.parseTime(text.toString())
        }
    )

    init {
        setText(Formatter.getTime(LocalTime.now()))
    }

    override fun requestDialog(fragmentManager: FragmentManager) {
        timePickerFragment.requestOpen(fragmentManager)
    }
}