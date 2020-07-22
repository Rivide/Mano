package com.example.mano.data.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mano.R
import com.example.mano.activities.EntryActivity
import com.example.mano.data.models.Entry
import com.example.mano.viewwrapper.ViewWrapper
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class EntryAdapter(val context: Context, val entries: Array<Entry>) :
  RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {

  val v = ViewWrapper.getWrapper()

  class EntryViewHolder(val layout: ConstraintLayout) : RecyclerView.ViewHolder(layout)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
    return EntryViewHolder(
      LayoutInflater.from(parent.context)
        .inflate(R.layout.layout_entry, parent, false) as ConstraintLayout
    )
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
    val entry = entries[position]

    val dateTime = LocalDateTime.ofEpochSecond(entry.dateTime, 0,
      OffsetDateTime.now().offset)

    ViewWrapper.viewGroup = holder.layout

    v(R.id.entryTitle).text = entry.title
    v(R.id.entryBody).text = entry.body
    v(R.id.entryDate).text = dateTime.format(DateTimeFormatter.ofPattern("MM/dd/yy"))
    v(R.id.entryTime).text = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

    holder.layout.setOnClickListener {
      val intent = Intent(context, EntryActivity::class.java)

      intent.apply {
        putExtra("id", entry.id)
        putExtra("title", entry.title)
        putExtra("body", entry.body)
        putExtra("dateTime", entry.dateTime)
      }

      context.startActivity(intent)
    }
  }

  override fun getItemCount(): Int {
    return entries.size
  }
}