package com.example.mano.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mano.R
import com.example.mano.data.adapters.ComponentAdapter
import com.example.mano.data.database.DBHelper
import com.example.mano.data.models.Component
import com.example.mano.data.models.Reminder
import com.example.mano.fragments.DateTimePicker
import com.example.mano.fragments.DeleteFragment
import com.example.mano.viewwrapper.ViewWrapper
import java.time.LocalDateTime
import java.time.OffsetDateTime

class EntryActivity : AppCompatActivity() {
  lateinit var dbHelper: DBHelper
  var id: Long = -1
  lateinit var components: MutableList<Component>
  lateinit var dateTimePicker: DateTimePicker
  var deleteFragment: DeleteFragment? = null
  val v = ViewWrapper.withParent(this)

  lateinit var recycler: RecyclerView

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_entry)

    components = ArrayList()

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

      components.addAll(dbHelper.selectComponentsByEntry(id)
        .sortedBy { it.position }.toTypedArray())
    } else {
      dateTimePicker = DateTimePicker(
        supportFragmentManager,
        findViewById(R.id.date),
        findViewById(R.id.time)
      )
    }

    updateRecycler()
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

    dbHelper.insertReminder(id, 0, dateTime)

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
  fun onAddComponentButtonClick(view: View) {
    components.add(Reminder(-1, id, components.size.toLong(),
      OffsetDateTime.now().toEpochSecond()))

    updateRecycler()
  }
  private fun updateRecycler() {
    val linearManager = LinearLayoutManager(this)

    val componentAdapter = ComponentAdapter(components)

    recycler = findViewById(R.id.recycler)

    recycler.apply {
      setHasFixedSize(true)

      layoutManager = linearManager

      adapter = componentAdapter
    }
  }
}