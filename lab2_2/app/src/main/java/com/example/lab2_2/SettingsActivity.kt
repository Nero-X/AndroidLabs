package com.example.lab2_2

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_settings.*
import petrov.kristiyan.colorpicker.ColorPicker
import petrov.kristiyan.colorpicker.ColorPicker.OnChooseColorListener


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        bgColor.setTextColor(intent.getIntExtra("bgColor", 0))
        statusBarColor.setTextColor(intent.getIntExtra("statusBarColor", 0))
        actionBarColor.setTextColor(intent.getIntExtra("actionBarColor", 0))
    }

    private fun createColorPickerDialog(id: Int) {
        val colorPicker = ColorPicker(this)
        colorPicker.show()
        colorPicker.setOnChooseColorListener(object : OnChooseColorListener {
            override fun onChooseColor(position: Int, color: Int) {
                when (id) {
                    0 -> bgColor.setTextColor(color)
                    1 -> statusBarColor.setTextColor(color)
                    2 -> actionBarColor.setTextColor(color)
                }
            }

            override fun onCancel() {}
        })
    }

    fun onClickButton(view: View) {
        when (view.id) {
            R.id.bgColor -> createColorPickerDialog(0)
            R.id.statusBarColor -> createColorPickerDialog(1)
            R.id.actionBarColor -> createColorPickerDialog(2)
            R.id.apply -> {
                val responseIntent = Intent()
                responseIntent.putExtra("bgColor", bgColor.currentTextColor)
                responseIntent.putExtra("statusBarColor", statusBarColor.currentTextColor)
                responseIntent.putExtra("actionBarColor", actionBarColor.currentTextColor)
                setResult(Activity.RESULT_OK, responseIntent)
                finish()
            }
        }
    }
}