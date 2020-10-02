package com.example.lab2_4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val x = 1.236
        val z = 4.5
        var y = z.pow(2)/(5+z.pow(2)/7)
        var a = (2*cos(x - PI/6))/(1/3 + sin(y + 2).pow(2))
        result.text = a.toString()
    }
}