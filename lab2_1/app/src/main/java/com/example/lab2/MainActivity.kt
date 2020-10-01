package com.example.lab2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_show.setOnClickListener {
            textView.visibility = View.VISIBLE
        }
        button_hide.setOnClickListener {
            textView.visibility = View.INVISIBLE
        }
    }
}
