package com.example.lab5

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.iterator
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var arr: Array<IntArray>
    private lateinit var zeroPos: Pair<Int, Int>
    private var rowsCount = 4
    private var cellsCount = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arr = initArray(rowsCount, cellsCount)
        zeroPos = arrSearch(0)
        shuffle()
        highlightMovable()
    }

    private val Button.intText: Int
    get() = this.text.toString().toInt()

    private val Button.index: Pair<Int, Int>
    get() = arrSearch(this.intText)

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

    private fun btnSearch(index: Pair<Int, Int>): Button
    {
        val tr = tableLayout[index.first] as TableRow
        return tr[index.second] as Button
    }

    private fun initArray(rowsCount: Int, cellsCount: Int): Array<IntArray>
    {
        val arr = Array(rowsCount) { i -> IntArray(cellsCount) { j -> i*cellsCount + j + 1} }
        arr[rowsCount - 1][cellsCount - 1] = 0
        return arr
    }

    private fun shuffle()
    {
        val moves = Random.nextInt(2, 8)
        for (i in 0..moves) {
            move(getMovable().random())
        }
    }

    private fun checkSolution()
    {
        for (i in arr.indices)
            for (j in arr[i].indices) {
                if (arr[i][j] != i*cellsCount + j + 1 && arr[i][j] != 0) return
            }

        // solved
        for (i in 0 until rowsCount)
            for (j in 0 until cellsCount) {
                val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.tile_anim)
                Timer().schedule((i*cellsCount + j + 1) * 100L) { runOnUiThread { btnSearch(Pair(i, j)).startAnimation(anim) } }
            }
    }

    private fun arrSwap(index1 : Pair<Int, Int>, index2: Pair<Int, Int> = zeroPos)
    {
        val a = arr[index1.first][index1.second]
        arr[index1.first][index1.second] = arr[index2.first][index2.second]
        arr[index2.first][index2.second] = a
    }

    private fun btnSwap(index1 : Pair<Int, Int>, index2: Pair<Int, Int> = zeroPos)
    {
        val tr1 = tableLayout[index1.first] as TableRow
        val btn1 = tr1[index1.second]
        val tr2 = tableLayout[index2.first] as TableRow
        val btn2 = tr2[index2.second]
        tr1.removeView(btn1)
        tr2.removeView(btn2)
        if (index1.second < index2.second)
        {
            tr1.addView(btn2, index1.second)
            tr2.addView(btn1, index2.second)
        }
        else
        {
            tr2.addView(btn1, index2.second)
            tr1.addView(btn2, index1.second)
        }
    }

    private fun move(btn: Pair<Int, Int>)
    {
        arrSwap(btn)
        btnSwap(btn)
        zeroPos = btn
    }

    private fun getMovable(): ArrayList<Pair<Int, Int>>
    {
        val lst = ArrayList<Pair<Int, Int>>()

        if (zeroPos.first > 0) lst.add(Pair(zeroPos.first - 1, zeroPos.second))
        if (zeroPos.first < rowsCount - 1) lst.add(Pair(zeroPos.first + 1, zeroPos.second))
        if (zeroPos.second > 0) lst.add(Pair(zeroPos.first, zeroPos.second - 1))
        if (zeroPos.second < cellsCount - 1) lst.add(Pair(zeroPos.first, zeroPos.second + 1))
        return lst
    }

    private fun highlightMovable()
    {
        for (tr in tableLayout)
            for (btn in (tr as TableRow))
            {
                btn.backgroundTintList = null
            }
        for (index in getMovable())
        {
            btnSearch(index).backgroundTintList = getColorStateList(R.color.highlighted)
        }
    }

    fun btnClickHandler(v: View)
    {
        val btn = v as Button

        if (btn.backgroundTintList == getColorStateList(R.color.highlighted))
        {
            move(btn.index)
            highlightMovable()
            checkSolution()
        }
    }
}