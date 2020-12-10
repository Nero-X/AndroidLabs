package com.example.lab9

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.graphics.drawable.toBitmap
import pl.droidsonroids.gif.GifDrawable
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.random.Random

class Draw2D @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {



    private lateinit var stars: Array<Shape>
    private val starCount = 5



    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        /*stars = Array(starCount) {

            return@Array star
        }*/

    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {


        }
    }

}