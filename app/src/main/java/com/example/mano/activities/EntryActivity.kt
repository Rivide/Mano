package com.example.mano.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.mano.R
import com.example.mano.data.database.DBHelper
import com.example.mano.fragments.DateTimePicker
import com.example.mano.fragments.DeleteFragment
import com.example.mano.fragments.TimePickerFragment
import com.example.mano.viewwrapper.ViewWrapper
import java.time.LocalDateTime
import java.time.OffsetDateTime
import kotlin.reflect.typeOf

class EntryActivity : AppCompatActivity() {
  lateinit var dbHelper: DBHelper
  var id: Long = -1
  lateinit var dateTimePicker: DateTimePicker
  var deleteFragment: DeleteFragment? = null
  val v = ViewWrapper.getWrapper()

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_entry)

    ViewWrapper.activity = this

    dbHelper = DBHelper(this)

    id = intent.getLongExtra("id", -1)

    if (id > 0) {
      v(R.id.title).text = intent.getStringExtra("title")!!
      v(R.id.body).text = intent.getStringExtra("body")!!

      dateTimePicker = DateTimePicker(
        supportFragmentManager,
        findViewById(R.id.date),
        findViewById(R.id.time),
        LocalDateTime.ofEpochSecond(intent.getLongExtra("dateTime", -1),
          0, OffsetDateTime.now().offset)
      )
      enableDelete()
    } else {
      dateTimePicker = DateTimePicker(
        supportFragmentManager,
        findViewById(R.id.date),
        findViewById(R.id.time)
      )
    }
  }
  @RequiresApi(Build.VERSION_CODES.O)
  fun onSaveButtonClick(view: View) {
    val title = v(R.id.title).text
    val body = v(R.id.body).text
    val dateTime = dateTimePicker.dateTime.toEpochSecond(OffsetDateTime.now().offset)

    if (id > 0) {
      dbHelper.updateEntry(id, title, body, dateTime)
    } else {
      id = dbHelper.insertEntry(title, body, dateTime)
    }

    enableDelete()
  }
  fun onDeleteButtonClick(view: View) {
    deleteFragment!!.show(supportFragmentManager, "delete")
  }
  fun onExitButtonClick(view: View) {
    startActivity(Intent(this, EntryListActivity::class.java))
  }
  fun enableDelete() {
    v(R.id.deleteButton).view.visibility = View.VISIBLE

    if (deleteFragment == null) {
      deleteFragment = DeleteFragment {
        dbHelper.deleteEntry(id)
        id = -1

        v(R.id.deleteButton).view.visibility = View.GONE
      }
    }
  }
}