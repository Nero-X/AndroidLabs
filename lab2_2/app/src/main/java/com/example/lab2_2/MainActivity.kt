package com.example.lab2_2

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var actionBarColor = R.color.colorPrimary

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
            R.id.settings -> {
                val requestIntent = Intent(this, SettingsActivity::class.java)
                requestIntent.putExtra("bgColor", constraintLayout.backgroundColor)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    requestIntent.putExtra("statusBarColor", window.statusBarColor)
                }
                requestIntent.putExtra("actionBarColor", actionBarColor)
                startActivityForResult(requestIntent, 0)
            }
        }
        return true
    }

    private var View.backgroundColor: Int
        get() = (background as? ColorDrawable?)?.color ?: Color.TRANSPARENT
        set(value) { this.setBackgroundColor(value) }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            constraintLayout.backgroundColor = data!!.getIntExtra("bgColor", 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = data.getIntExtra("statusBarColor", 0)
            }
            actionBarColor = data.getIntExtra("actionBarColor", 0)
            supportActionBar?.setBackgroundDrawable(actionBarColor.toDrawable())
        }
    }
}