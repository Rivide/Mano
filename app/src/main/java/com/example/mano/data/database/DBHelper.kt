package com.example.mano.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mano.data.models.Entry

const val DB_NAME = "Mano.db"

class DBHelper(context: Context) : SQLiteOpenHelper(context,
  DB_NAME, null, 2) {
  override fun onCreate(db: SQLiteDatabase) {
    db.execSQL(
      "create table entry (id integer primary key, title text, body text, dateTime integer)"
    )
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    db.execSQL("DROP TABLE IF EXISTS entry")
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
}