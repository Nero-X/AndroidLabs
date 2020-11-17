package com.example.lab8

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickListener(v: View) {
        val intent = Intent(this, NotificationService::class.java)
            .putExtra("Type", v.id)
            .putExtra("Delay", editText_delay.text.toString().toInt())
        startService(intent)
    }
}