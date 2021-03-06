package com.example.mano.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mano.R
import com.example.mano.data.models.Component
import com.example.mano.data.models.Reminder
import com.example.mano.formatter.Formatter
import com.example.mano.viewwrapper.ViewWrapper

class ComponentAdapter(private val components: List<Component>, val onDeleteComponent: (Int) -> Unit) :
    RecyclerView.Adapter<ComponentAdapter.ComponentViewHolder>() {

    private val viewTypes = arrayOf("reminder")

    class ComponentViewHolder(val layout: ConstraintLayout) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        return ComponentViewHolder(
            when (viewTypes[viewType]) {
                "reminder" ->
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_reminder, parent, false) as ConstraintLayout
                else -> ConstraintLayout(parent.context)
            }
        )
    }

    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        val component = components[position]

        val v = ViewWrapper.withParent(holder.layout)

        v(R.id.deleteComponentButton).view.setOnClickListener {
            onDeleteComponent(position)
        }

        when (component.type) {
            "reminder" -> {
                val reminder = component as Reminder

                v(R.id.reminderDate).text = Formatter.getDate(reminder.dateTime)
                v(R.id.reminderTime).text = Formatter.getTime(reminder.dateTime)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewTypes.indexOf(components[position].type)
    }

    override fun getItemCount(): Int {
        return components.size
    }
}