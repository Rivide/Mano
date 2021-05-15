package com.example.mano.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.mano.data.models.Component
import com.example.mano.data.models.Entry
import com.example.mano.data.models.Reminder

const val DB_NAME = "Mano.db"
const val TAG = "DBHelper"

class DBHelper(context: Context) : SQLiteOpenHelper(context,
  DB_NAME, null, 4) {
  override fun onCreate(db: SQLiteDatabase) {
    Log.d(TAG, "$DB_NAME created.")
    /*db.execSQL(
      "create table entry (id integer primary key, title text, body text, dateTime integer)"
    )*/
    /*db.execSQL(
      "create table component (id integer primary key, entryId integer, position integer, type text)"
    )*/
    /*db.execSQL(
      "create table reminder (id integer primary key, dateTime integer)"
    )*/
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    Log.d(TAG, "$DB_NAME upgraded.")
    db.execSQL(
      "create table reminder (id integer primary key, dateTime integer)"
    )
    //db.execSQL("DROP TABLE IF EXISTS components")
    onCreate(db)
  }

  fun insertEntry(title: String, body: String, dateTime: Long): Long {
    val values = ContentValues()
    values.put("title", title)
    values.put("body", body)
    values.put("dateTime", dateTime)

    return writableDatabase.insert("entry", null, values)
  }

  fun updateEntry(id: Long, title: String, body: String, dateTime: Long) {
    val values = ContentValues()
    values.put("title", title)
    values.put("body", body)
    values.put("dateTime", dateTime)

    writableDatabase.update("entry", values, "id=?", arrayOf(id.toString()))
  }

  fun deleteEntry(id: Long) {
    writableDatabase.delete("entry", "id=?", arrayOf(id.toString()))
  }

  fun selectAllEntries(): ArrayList<Entry> {
    val list = ArrayList<Entry>()

    val cursor = readableDatabase.rawQuery("select * from entry", null)
    cursor.moveToFirst()

    while (!cursor.isAfterLast) {
      list.add(Entry(
        cursor.getLong(cursor.getColumnIndex("id")),
        cursor.getString(cursor.getColumnIndex("title")),
        cursor.getString(cursor.getColumnIndex("body")),
        cursor.getLong(cursor.getColumnIndex("dateTime"))
      ))
      cursor.moveToNext()
    }
    return list
  }

  fun insertComponent(entryId: Long, position: Long, type: String): Long {
    val values = ContentValues()
    values.put("entryId", entryId)
    values.put("position", position)
    values.put("type", type)

    return writableDatabase.insert("component", null, values)
  }
  fun selectComponentsByEntry(entryId: Long): ArrayList<Component> {
    val list = ArrayList<Component>()

    val cursor = readableDatabase.rawQuery(
      "select * from component where entryId = ?",
      arrayOf(entryId.toString())
    )

    cursor.moveToFirst()

    while (!cursor.isAfterLast) {
      val component = Component(
        cursor.getLong(cursor.getColumnIndex("id")),
        cursor.getLong(cursor.getColumnIndex("entryId")),
        cursor.getLong(cursor.getColumnIndex("position")),
        cursor.getString(cursor.getColumnIndex("type"))
      )

      list.add(
        selectTypedComponent(component)
      )

      cursor.moveToNext()
    }
    return list
  }

  fun selectTypedComponent(component: Component): Component {
    val cursor = readableDatabase.rawQuery("select * from ${component.type} where id = ?",
      arrayOf(component.id.toString()))

    cursor.moveToFirst()

    return when (component.type) {
      "reminder" -> Reminder(component.id, component.entryId, component.position,
        cursor.getLong(cursor.getColumnIndex("dateTime")))

      else -> component
    }
  }

  fun deleteComponent(component: Component) {

  }

  fun insertReminder(entryId: Long, position: Long, dateTime: Long): Long {
    val values = ContentValues()
    values.put("id", insertComponent(entryId, position, "reminder"))
    //values.put("entryId", entryId)
    //values.put("position", position)
    values.put("dateTime", dateTime)

    return writableDatabase.insert("reminder", null, values)
  }

  fun selectRemindersByEntry(entryId: Long): ArrayList<Reminder> {
    val list = ArrayList<Reminder>()

    val cursor = readableDatabase.rawQuery(
      "select * from reminder where entryId = ?",
      arrayOf(entryId.toString())
    )
    cursor.moveToFirst()

    while (!cursor.isAfterLast) {
      list.add(Reminder(
        cursor.getLong(cursor.getColumnIndex("id")),
        cursor.getLong(cursor.getColumnIndex("entryId")),
        cursor.getLong(cursor.getColumnIndex("index")),
        cursor.getLong(cursor.getColumnIndex("dateTime"))
      ))
      cursor.moveToNext()
    }
    return list
  }
}