package com.example.lab9

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class Moon @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private var phase = -1f
    private val moon = Shape(Color.YELLOW)

    init {
        Timer().scheduleAtFixedRate(0, 100L) { invalidate() }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            moon.setCrescent(width - 200f, 100f, 150f, phase)
            canvas.drawPath(moon.path, moon.paint)
            phase += 0.01f
            if (phase > 1) phase = -1f
        }
    }
}