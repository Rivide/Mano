package com.example.mano.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mano.R

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
  fun onEntryButtonClick(button: View?) {
    startActivity(Intent(this, EntryActivity::class.java))
  }

  fun onEntriesButtonClick(button: View?) {
    startActivity(Intent(this, EntryListActivity::class.java))
  }
}