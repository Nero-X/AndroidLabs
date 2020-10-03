package com.example.lab2_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_add.setOnClickListener {
            textView_res.text =
                (num1.text.toString().toInt() + num2.text.toString().toInt()).toString()
        }
        button_sub.setOnClickListener {
            textView_res.text =
                (num1.text.toString().toInt() - num2.text.toString().toInt()).toString()
        }
        button_mul.setOnClickListener {
            textView_res.text =
                (num1.text.toString().toInt() * num2.text.toString().toInt()).toString()
        }
        button_div.setOnClickListener {
            textView_res.text = (num1.text.toString().toDouble() / num2.text.toString().toInt()).toString()
            if (num2.text.toString().toInt() == 0)
            {
                var toast = Toast.makeText(this, "Деление на ноль!", Toast.LENGTH_LONG)
                var imgView = ImageView(applicationContext)
                    imgView.setImageResource(R.drawable.zero)
                var toastContainer = toast.view as LinearLayout
                    toastContainer.addView(imgView, 450, 450)
                toast.show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.add -> button_add.callOnClick()
            R.id.sub -> button_sub.callOnClick()
            R.id.mul -> button_mul.callOnClick()
            R.id.div -> button_div.callOnClick()
        }
        return true
    }
}