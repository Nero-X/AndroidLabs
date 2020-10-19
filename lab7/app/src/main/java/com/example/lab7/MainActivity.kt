package com.example.lab7

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import androidx.core.view.iterator
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.min


class MainActivity : AppCompatActivity() {
    val MAX_TEXT_SIZE = 120f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (tr in tableLayout) {
            ((tr as TableRow)[0] as EditText).addLayoutWatcher()
            for (editText in tr) {
                (editText as EditText).addTextWatcher()
            }
        }
    }

    fun EditText.addTextWatcher() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val tr = parent as TableRow
                if (s != null) {
                    when {
                        this@addTextWatcher == tr.children.last() && after != 0 ->
                            for (row in tableLayout) {
                                val v = layoutInflater.inflate(R.layout.custom_edit_text, row as TableRow, false)
                                (v as EditText).addTextWatcher()
                                row.addView(v)
                            }
                        this@addTextWatcher == tr[tr.childCount - 2] && after == 0 && s.count() == count -> {
                            var canDelete = true
                            for (row in tableLayout) {
                                canDelete = canDelete && ((row as TableRow)[tr.childCount - 1] as EditText).text.isNullOrBlank()
                            }
                            if (canDelete) {
                                for (row in tableLayout) {
                                    (row as TableRow).removeViewAt(tr.childCount - 1)
                                }
                            }
                        }
                    }
                    when {
                        tr == tableLayout.children.last() && after != 0 -> {
                            val row = TableRow(baseContext)
                            val colCount = (tableLayout[0] as TableRow).childCount
                            for (i in 0 until colCount) {
                                val v = layoutInflater.inflate(R.layout.custom_edit_text, row, false)
                                (v as EditText).addTextWatcher()
                                row.addView(v)
                            }
                            tableLayout.addView(row)
                        }
                        tr == tableLayout[tableLayout.childCount - 2] && after == 0 && s.count() == count -> {
                            var canDelete = true
                            for (editText in tr) {
                                //if (editText != this@addTextWatcher) canDelete = canDelete && (editText as EditText).text.isNullOrBlank()
                            }
                            if (canDelete) tableLayout.removeView(tr)
                        }
                    }
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    //val tr = parent as TableRow
                    var minTextSize = MAX_TEXT_SIZE
                    for (tr in tableLayout)
                        for (editText in tr as TableRow) {
                            val size = (editText as EditText).getPreferredTextSize()
                            if (size < minTextSize) minTextSize = size
                        }
                    for (tr in tableLayout)
                        for (editText in tr as TableRow) {
                            (editText as EditText).textSize = minTextSize
                        }
                }
            }
        })
    }

    fun EditText.getPreferredTextSize() : Float {
        return min((right - left) / (text.toString().count() * 1.9f), MAX_TEXT_SIZE)
    }

    private fun EditText.getPreferredTextSize(width: Int) : Float {
        return min(width / (text.toString().count() * 1.9f), MAX_TEXT_SIZE)
    }

    private fun EditText.addLayoutWatcher() {
        addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (left != 0 || top != 0 || right != 0 || bottom != 0) {
                //val tr = parent as TableRow
                var minTextSize = MAX_TEXT_SIZE
                for (tr in tableLayout)
                    for (editText in tr as TableRow) {
                        val size = (editText as EditText).getPreferredTextSize(right - left)
                        if (size < minTextSize) minTextSize = size
                    }
                for (tr in tableLayout)
                    for (editText in tr as TableRow) {
                        (editText as EditText).textSize = minTextSize
                    }
            }
        }
    }
}