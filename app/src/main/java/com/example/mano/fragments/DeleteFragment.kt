package com.example.mano.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.mano.R

class DeleteFragment(val onDelete: () -> Unit) : DialogFragment() {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val layout = inflater.inflate(R.layout.layout_delete_dialog, container)

    layout.findViewById<Button>(R.id.deleteButton).setOnClickListener {
      dismiss()
      onDelete()
    }
    layout.findViewById<Button>(R.id.cancelButton).setOnClickListener { dismiss() }

    return layout
  }
}