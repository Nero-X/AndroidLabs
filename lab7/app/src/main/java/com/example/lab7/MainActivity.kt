package com.example.lab7

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Size
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import androidx.core.view.iterator
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.min
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    val MAX_TEXT_SIZE = 120f
    val WIDTH_CONST = 1.9f
    val HEIGHT_CONST = 5f
    val MIN_ROWS_COUNT = 2
    val MIN_COLUMN_COUNT = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonRnd.setOnClickListener {
            for (i in 0..tableLayout.childCount - 2) {
                for (j in 0..(tableLayout[0] as TableRow).childCount - 2) {
                    ((tableLayout[i] as TableRow)[j] as EditText).setText(Random.nextInt(100).toString())
                }
            }
        }

        buttonCalc.setOnClickListener {
            outerLoop@for (col in 0 until linearLayout.childCount) {
                for (row in 0..tableLayout.childCount - 2) {
                    val value = ((tableLayout[row] as TableRow)[col] as EditText).text.toString()
                    if (value != "" && value.toInt() % 2 == 0) {
                        (linearLayout[col] as TextView).text = value
                        continue@outerLoop
                    }
                }
                (linearLayout[col] as TextView).text = "0"
            }
        }

        for (tr in tableLayout) {
            //((tr as TableRow)[0] as EditText).addLayoutWatcher()
            for (editText in tr as TableRow) {
                (editText as EditText).addTextWatcher()
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val rowCount = tableLayout.childCount - 1
        val colCount = linearLayout.childCount
        val arr = Array(rowCount * colCount) {""}
        for (i in 0 until rowCount)
            for (j in 0 until colCount) arr[i * rowCount + j] = ((tableLayout[i] as TableRow)[j] as EditText).text.toString()
        outState.putSize("matrixSize", Size(colCount, rowCount))
        outState.putStringArray("values", arr)
    }

    fun TableLayout.addRows(rowCount: Int, colCount: Int) {
        for (i in 0 until rowCount) {
            val row = TableRow(baseContext)
            row.id = View.generateViewId()
            row.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            for (j in 0 until colCount) {
                val v = layoutInflater.inflate(R.layout.custom_edit_text, row, false)
                v.id = View.generateViewId()
                (v as EditText).addTextWatcher()
                row.addView(v)
            }
            tableLayout.addView(row)
        }
    }

    fun EditText.addTextWatcher() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val tr = parent as TableRow
                if (s != null) {
                    when { // columns
                        this@addTextWatcher == tr.children.last() && after != 0 -> { // add
                            for (row in tableLayout) {
                                val v = layoutInflater.inflate(R.layout.custom_edit_text, row as TableRow, false)
                                (v as EditText).addTextWatcher()
                                row.addView(v)
                            }
                            val v = TextView(baseContext)
                            v.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                            v.textSize = 24f
                            v.text = "0"
                            v.textAlignment = View.TEXT_ALIGNMENT_GRAVITY
                            linearLayout.addView(v)
                        }
                        this@addTextWatcher == tr[tr.childCount - 2] && tr.childCount > MIN_COLUMN_COUNT && after == 0 && s.count() == count -> { // remove
                            var canDelete = true
                            val index = tr.childCount - 2
                            for (row in tableLayout) {
                                val editText = (row as TableRow)[index] as EditText
                                if (editText != this@addTextWatcher) canDelete = canDelete && editText.text.isNullOrBlank()
                            }
                            if (canDelete) {
                                for (row in tableLayout) {
                                    (row as TableRow).removeViewAt(index)
                                }
                                linearLayout.removeViewAt(index)
                            }
                        }
                    }
                    when { // rows
                        tr == tableLayout.children.last() && after != 0 -> { // add
                            val row = TableRow(baseContext)/////////// store min textsize, recalculate when needed
                            row.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                            val colCount = (tableLayout[0] as TableRow).childCount
                            for (i in 0 until colCount) {
                                val v = layoutInflater.inflate(R.layout.custom_edit_text, row, false)
                                (v as EditText).addTextWatcher()
                                row.addView(v)
                            }
                            tableLayout.addView(row)
                        }
                        tr == tableLayout[tableLayout.childCount - 2] && tableLayout.childCount > MIN_ROWS_COUNT && after == 0 && s.count() == count -> { // remove
                            var canDelete = true
                            for (editText in tr) {
                                if (editText != this@addTextWatcher) canDelete = canDelete && (editText as EditText).text.isNullOrBlank()
                            }
                            if (canDelete) tableLayout.removeView(tr)
                        }
                    }
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && (left != 0 || top != 0 || right != 0 || bottom != 0)) {
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
        return min((right - left) / (text.toString().count() * WIDTH_CONST), bottom / HEIGHT_CONST)
    }

    private fun EditText.getPreferredTextSize(width: Int, height: Int) : Float {
        return min(width / (text.toString().count() * WIDTH_CONST), height / HEIGHT_CONST)
    }

    private fun EditText.addLayoutWatcher() {
        addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (left != 0 || top != 0 || right != 0 || bottom != 0) {
                var minTextSize = MAX_TEXT_SIZE
                for (tr in tableLayout)
                    for (editText in tr as TableRow) {
                        val size = (editText as EditText).getPreferredTextSize(right - left, bottom)
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