package com.example.mano.activities

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mano.R
import com.example.mano.data.adapters.ComponentAdapter
import com.example.mano.data.database.DBHelper
import com.example.mano.data.models.Component
import com.example.mano.data.models.Reminder
import com.example.mano.formatter.Formatter
import com.example.mano.fragments.DateTimePicker
import com.example.mano.fragments.DeleteFragment
import com.example.mano.viewwrapper.ViewWrapper
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

class EntryActivity : AppCompatActivity() {
  lateinit var dbHelper: DBHelper
  var entryId: Long = -1
  var deleteFragment: DeleteFragment? = null

  val v = ViewWrapper.withParent(this)

  lateinit var componentUIManager: ComponentUIManager

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_entry)

    componentUIManager = ComponentUIManager(this)

    dbHelper = DBHelper(this)

    entryId = intent.getLongExtra("id", -1)

    if (entryId > 0) {
      v(R.id.title).text = intent.getStringExtra("title")!!
      v(R.id.body).text = intent.getStringExtra("body")!!

      val epochSecond = intent.getLongExtra("dateTime", -1)

      v(R.id.date).text = Formatter.getDate(epochSecond)
      v(R.id.time).text = Formatter.getTime(epochSecond)

      enableDelete()

      componentUIManager = ComponentUIManager(this, dbHelper.selectComponentsByEntry(entryId)
        .sortedBy { it.position }.toTypedArray().toMutableList())
    } else {
      componentUIManager = ComponentUIManager(this)
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  fun onSaveButtonClick(view: View) {
    val title = v(R.id.title).text
    val body = v(R.id.body).text
    val dateTime = LocalDateTime.of(
      Formatter.parseDate(v(R.id.date).text),
      Formatter.parseTime(v(R.id.time).text)
    ).toEpochSecond(OffsetDateTime.now().offset)

    if (entryId > 0) {
      dbHelper.updateEntry(entryId, title, body, dateTime)
    } else {
      entryId = dbHelper.insertEntry(title, body, dateTime)
    }

    componentUIManager.onSave()

    componentUIManager.components.forEachIndexed{ index, component ->
      if (component.id > 0) {
        dbHelper.updateReminder(component.id, (component as Reminder).dateTime)
      }
      else {
        dbHelper.insertReminder(entryId, index.toLong(), (component as Reminder).dateTime)
      }
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
        dbHelper.deleteEntry(entryId)
        entryId = -1

        v(R.id.deleteButton).view.visibility = View.GONE
      }
    }
  }

  fun onAddComponentButtonClick(view: View) {
    componentUIManager.onAddComponent(entryId)
  }

  class ComponentUIManager(activity: Activity,
                           val components: MutableList<Component> = ArrayList()) {
    private val linearManager = LinearLayoutManager(activity)
    private val componentAdapter = ComponentAdapter(components)
    private val recycler: RecyclerView = activity.findViewById(R.id.recycler)

    init {
      recycler.apply {
        setHasFixedSize(true)

        layoutManager = linearManager

        adapter = componentAdapter
      }
    }

    fun onAddComponent(entryId: Long) {
      components.add(Reminder(-1, entryId, components.size.toLong(),
        OffsetDateTime.now().toEpochSecond()))
      updateData()
      render()
    }

    fun onSave() {
      updateData()
    }

    private fun updateData() {
      for (i in 0 until linearManager.childCount) {
        val v = ViewWrapper.withParent(linearManager.findViewByPosition(i)!!)

        when (val component = components[i]) {
          is Reminder -> {
            component.dateTime = LocalDateTime.of(
              Formatter.parseDate(v(R.id.reminderDate).text),
              Formatter.parseTime(v(R.id.reminderTime).text)
            ).toEpochSecond(OffsetDateTime.now().offset)
          }
        }
      }
    }

    private fun render() {
      componentAdapter.notifyDataSetChanged()
    }
  }
}