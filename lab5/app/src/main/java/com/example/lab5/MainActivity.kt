package com.example.lab5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableRow
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.ArrayList

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

    val Button.intText: Int
    get() = this.text.toString().toInt()

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

    private fun arrSwap(index1 : Pair<Int, Int>, index2: Pair<Int, Int>)
    {
        val a = arr[index1.first][index1.second]
        arr[index1.first][index1.second] = arr[index2.first][index2.second]
        arr[index2.first][index2.second] = a
    }

    private fun btnSwap(index1 : Pair<Int, Int>, index2: Pair<Int, Int>)
    {
        val tr1 = tableLayout[index1.first] as TableRow
        val btn1 = tr1[index1.second]
        val tr2 = tableLayout[index2.first] as TableRow
        val btn2 = tr2[index2.second]
        tr1.removeViewAt(index1.second)
        tr2.removeViewAt(index2.second)
        tr1.addView(btn2, index2.second)
        tr2.addView(btn1, index1.second)
    }

    private fun getMovable(): ArrayList<Pair<Int, Int>>
    {
        val zero = arrSearch(0)
        val lst = ArrayList<Pair<Int, Int>>()

        if (zero.first > 0) lst.add(Pair(zero.first - 1, zero.second))
        if (zero.first < rowsCount - 1) lst.add(Pair(zero.first + 1, zero.second))
        if (zero.second > 0) lst.add(Pair(zero.first, zero.second - 1))
        if (zero.second < cellsCount) lst.add(Pair(zero.first, zero.second + 1))
        return lst
    }

    fun btnClickHandler(v: View)
    {
        val btn = v as Button

        val index = arrSearch(btn.intText)
        try {
            when
            {
                arr[index.first - 1][index.second] == 0 ->
                {
                    arrSwap(index, Pair(index.first - 1, index.second))
                    btnSwap(index, Pair(index.first - 1, index.second))
                }
            }
        }
        catch (e: Exception) {}

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
        //btn.backgroundTintList = getColorStateList(R.color.colorAccent)
        //((tableLayout[1] as TableRow)[2] as Button).background = getDrawable(R.drawable.button_highlighted)
    }
}