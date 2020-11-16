package com.example.lab8

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickListener(v: View) {
        /*val intent = Intent(this, NotificationService::class.java)
            .putExtra("Type", v.id)
            .putExtra("Delay", editText_delay.text.toString().toInt())
        startService(intent)*/

        val delay = editText_delay.text.toString().toInt()

        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
            .putExtra("Type", v.id)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + delay * 1000, delay * 1000L, pendingIntent)
    }
}