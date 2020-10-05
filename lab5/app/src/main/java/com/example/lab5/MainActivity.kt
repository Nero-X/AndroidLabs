package com.example.lab5

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableRow
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var arr: Array<IntArray>
    //private lateinit var statesArr: Array<Array<State>>
    var rowsCount = 4
    var cellsCount = 4

    //enum class State{ DEFAULT, SELECTED, HIGHLIGHTED }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arr = initArray(rowsCount, cellsCount)
        //statesArr = Array(rowsCount) { Array(cellsCount) { State.DEFAULT } }
    }

    /*var Button.state: State
        get()
        {
            val index = arrSearch(this.text.toString().toInt())
            return statesArr[index.first][index.second]
        }
        set(value)
        {
            val index = arrSearch(this.text.toString().toInt())
            statesArr[index.first][index.second] = value
            // change appearance
        }*/

    private fun arrSearch(value: Int): Pair<Int, Int>
    {
        var row = 0
        var cell = 0
        outerLoop@for (i in arr.indices)
            for (j in arr[i].indices)
            {
                if (arr[i][j] == value)
                {
                    row = i
                    cell = j
                    break@outerLoop
                }
            }
        return row to cell
    }

    private fun initArray(rowsCount: Int, cellsCount: Int): Array<IntArray>
    {
        val arr = Array(rowsCount) { i -> IntArray(cellsCount) { j -> i*cellsCount + j + 1} }
        arr[rowsCount - 1][cellsCount - 1] = 0
        return arr
    }

    fun btnClickHandler(v: View)
    {
        val btn = v as Button
        val tr3 = tableLayout[3] as TableRow
        val a2 = tr3[2]
        tr3.removeViewAt(2)
        tr3.addView(a2)

        /*when (btn.state)
        {
            State.DEFAULT ->
            {
                // clear all states
                btn.state = State.SELECTED

            }
            State.SELECTED ->
            {
                // clear all states
                //btn.state = State.DEFAULT
            }
            State.HIGHLIGHTED ->
            {
                // swap and clear states
            }
        }*/
        //btn.background = getDrawable(R.drawable.button_highlighted)
        //((tableLayout[1] as TableRow)[2] as Button).background = getDrawable(R.drawable.button_highlighted)
    }
}