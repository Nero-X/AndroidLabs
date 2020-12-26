package com.example.lab11

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openMap.setOnClickListener {
            val coordRegex = Regex("^(-?\\d+(\\.\\d+)?),\\s*(-?\\d+(\\.\\d+)?)\$")
            var geoURI = ""
            geoURI = if (coordRegex.matches(address.text)) {
                "geo:${address.text}z=8"
            } else {
                "geo:0,0?q=${address.text}&z=8"
            }
            val geo = Uri.parse(geoURI)
            val geoIntent = Intent(Intent.ACTION_VIEW, geo)
            startActivity(geoIntent)
        }

        openStreet.setOnClickListener {
            val coordRegex = Regex("^(-?\\d+(\\.\\d+)?),\\s*(-?\\d+(\\.\\d+)?)\$")
            var geoURI = ""
            geoURI = if (coordRegex.matches(address.text)) {
                "google.streetview:cbll=${address.text}&cbp=1,99.56,,1,2.0&mz=19"
            } else {
                "geo:0,0?q=${address.text}&z=8"
            }
            val geo = Uri.parse(geoURI)
            val geoIntent = Intent(Intent.ACTION_VIEW, geo)
            startActivity(geoIntent)
        }
    }
}