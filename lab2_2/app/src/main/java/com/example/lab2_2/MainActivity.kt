package com.example.lab2_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_add.setOnClickListener {
            textView_res.text = (num1.text.toString().toInt() + num2.text.toString().toInt()).toString()
        }
        button_sub.setOnClickListener {
            textView_res.text = (num1.text.toString().toInt() - num2.text.toString().toInt()).toString()
        }
        button_mul.setOnClickListener {
            textView_res.text = (num1.text.toString().toInt() * num2.text.toString().toInt()).toString()
        }
        button_div.setOnClickListener {
            try {
                textView_res.text = (num1.text.toString().toDouble() / num2.text.toString().toInt()).toString()
            }
            catch (e: ArithmeticException)
            {
                textView_res.text = "\u221e"
            }
        }
    }
}