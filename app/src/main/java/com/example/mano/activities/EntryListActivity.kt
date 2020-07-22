package com.example.mano.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mano.R
import com.example.mano.data.adapters.EntryAdapter
import com.example.mano.data.database.DBHelper
import com.example.mano.data.models.Entry

class EntryListActivity : AppCompatActivity() {
  fun onDisplay() {
    val linearManager = LinearLayoutManager(this)
    val entryAdapter = EntryAdapter(this,
      DBHelper(this).selectAllEntries().sortedByDescending { it.dateTime }.toTypedArray())

    findViewById<RecyclerView>(R.id.recycler).apply {
      setHasFixedSize(true)

      layoutManager = linearManager

      adapter = entryAdapter
    }
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_entry_list)
    onDisplay()
  }

  override fun onRestart() {
    super.onRestart()
    onDisplay()
  }
}