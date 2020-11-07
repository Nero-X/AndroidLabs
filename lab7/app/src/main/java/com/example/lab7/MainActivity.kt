package com.example.lab7

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import androidx.core.view.iterator
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    val MAX_TEXT_SIZE = 120f
    val WIDTH_CONST = 1.9f
    val HEIGHT_CONST = 5f
    val MIN_ROW_COUNT = 2
    val MIN_COLUMN_COUNT = 2
    val MAX_ROW_COUNT = 9
    val MAX_COLUMN_COUNT = 9
    var currentTextSize = MAX_TEXT_SIZE
    var newTextSize = MAX_TEXT_SIZE
    var currentMinEditText: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            val rowCount = savedInstanceState.getInt("rowCount")
            val colCount = savedInstanceState.getInt("colCount")
            val arr = savedInstanceState.getStringArray("values")!!
            tableLayout.addRows(rowCount + 1)
            tableLayout.addColumns(colCount + 1)
            for (i in 0 until rowCount)
                for (j in 0 until colCount) {
                    tableLayout[i, j] = arr[i * colCount + j]
                }
        }
        else {
            tableLayout.addRows(2)
            tableLayout.addColumns(2)
        }

        buttonRnd.setOnClickListener {
            for (i in 0..tableLayout.childCount - 2) {
                for (j in 0..(tableLayout[0] as TableRow).childCount - 2) {
                    tableLayout[i, j] = Random.nextInt(100).toString()
                }
            }
        }

        buttonCalc.setOnClickListener {
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                outerLoop@ for (col in 0 until linearLayout.childCount) {
                    for (row in 0..tableLayout.childCount - 2) {
                        val value = tableLayout[row, col]
                        if (value != "" && value.toInt() % 2 == 0) {
                            (linearLayout[col] as TextView).text = value
                            continue@outerLoop
                        }
                    }
                    (linearLayout[col] as TextView).text = "0"
                }
            }
            else {
                outerLoop@for (i in 0..tableLayout.childCount - 2) {
                    for (j in 0 until (tableLayout[0] as TableRow).childCount) {
                        val value = tableLayout[i, j]
                        if (value != "" && value.toInt() % 2 == 0) {
                            (linearLayout[i] as TextView).text = value
                            continue@outerLoop
                        }
                    }
                    (linearLayout[i] as TextView).text = "0"
                }
            }
        }
    }

    operator fun TableLayout.get(row: Int, column: Int): String =
        ((this[row] as TableRow)[column] as EditText).text.toString()

    operator fun TableLayout.set(row: Int, column: Int, value: String) {
        ((this[row] as TableRow)[column] as EditText).setText(value)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val rowCount = tableLayout.childCount - 1
        val colCount = (tableLayout[0] as TableRow).childCount - 1
        val arr = Array(rowCount * colCount) {""}
        for (i in 0 until rowCount)
            for (j in 0 until colCount) arr[i * colCount + j] = tableLayout[i, j]
        outState.putInt("rowCount", rowCount)
        outState.putInt("colCount", colCount)
        outState.putStringArray("values", arr)
    }

    fun TableLayout.addRows(count: Int = 1) {
        val colCount = if (tableLayout.childCount > 0) (tableLayout[0] as TableRow).childCount else 0
        for (i in 0 until count) {
            val row = TableRow(baseContext)
            for (j in 0 until colCount) {
                val v = layoutInflater.inflate(R.layout.custom_edit_text, row, false)
                (v as EditText).addTextWatcher()
                v.addLayoutWatcher()
                row.addView(v)
            }
            this.addView(row)
            row.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) linearLayout.addTextView()
        }
    }

    fun TableLayout.addColumns(count: Int = 1) {
        for (i in 0 until count) {
            for (row in this) {
                val v = layoutInflater.inflate(R.layout.custom_edit_text, row as TableRow, false)
                (v as EditText).addTextWatcher()
                v.addLayoutWatcher()
                row.addView(v)
            }
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) linearLayout.addTextView()
        }
    }

    private fun LinearLayout.addTextView() {
        val v = TextView(baseContext)
        v.layoutParams =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            else LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f)
        v.textSize = 24f
        v.text = "0"
        v.textAlignment = View.TEXT_ALIGNMENT_CENTER
        this.addView(v)
    }

    fun EditText.addTextWatcher() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val tr = parent as TableRow
                if (s != null) {
                    when { // columns
                        this@addTextWatcher == tr.children.last() && tr.childCount < MAX_COLUMN_COUNT && after != 0 -> { // add
                            tableLayout.addColumns()
                        }
                        tr.childCount > MIN_COLUMN_COUNT && this@addTextWatcher == tr[tr.childCount - 2] && after == 0 && s.count() == count -> { // remove
                            /*var canDelete = true
                            val index = tr.childCount - 2
                            for (row in tableLayout) {
                                val editText = (row as TableRow)[index] as EditText
                                if (editText != this@addTextWatcher) canDelete = canDelete && editText.text.isNullOrBlank()
                            }
                            if (canDelete) {
                                for (row in tableLayout) {
                                    (row as TableRow).removeViewAt(index)
                                }
                                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) linearLayout.removeViewAt(index)
                            }*/
                        }
                    }
                    when { // rows
                        tr == tableLayout.children.last() && tableLayout.childCount < MAX_ROW_COUNT && after != 0 -> { // add
                            /////////// store min textsize, recalculate when needed
                            tableLayout.addRows()
                        }
                        tableLayout.childCount > MIN_ROW_COUNT && tr == tableLayout[tableLayout.childCount - 2] && after == 0 && s.count() == count -> { // remove
                            /*var canDelete = true
                            for (editText in tr) {
                                if (editText != this@addTextWatcher) canDelete = canDelete && (editText as EditText).text.isNullOrBlank()
                            }
                            if (canDelete) {
                                tableLayout.removeView(tr)
                                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) linearLayout.removeViewAt(linearLayout.childCount - 2)
                            }*/
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
                    setTxtSize(minTextSize)

                    /*val new = this@addTextWatcher.getPreferredTextSize()
                    if (currentMinEditText == null || currentMinEditText!!.getPreferredTextSize() > new) {
                        currentMinEditText = this@addTextWatcher
                        setTxtSize(new)
                    }*/
                }
            }
        })
    }

    /*fun updateMinTextSize() {
        currentTextSize = MAX_TEXT_SIZE
        for (tr in tableLayout)
            for (editText in tr as TableRow) {
                val size = (editText as EditText).getPreferredTextSize()
                if (size < currentTextSize) {
                    currentTextSize = size
                    currentMinEditText = editText
                }
            }
    }*/

    fun setTxtSize(size: Float) {
        currentTextSize = size
        for (tr in tableLayout)
            for (editText in tr as TableRow) {
                (editText as EditText).textSize = size
            }
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
                val size = this.getPreferredTextSize(right - left, bottom)
                if (size < currentTextSize) setTxtSize(size)
            }
        }
    }
}