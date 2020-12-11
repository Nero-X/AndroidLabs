package com.example.lab9

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

class Background @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val paint = Paint()
    private lateinit var bg: LinearGradient

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        bg = LinearGradient(0f, 0f, 0f,  height.toFloat(), resources.getColor(R.color.gradient1), resources.getColor(R.color.gradient2), Shader.TileMode.MIRROR)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            paint.shader = bg
            canvas.drawPaint(paint)

            paint.shader = null
            paint.color = resources.getColor(R.color.ground)
            canvas.drawRect(0f, height * 0.8f, width.toFloat(), height.toFloat(), paint)
        }
    }
}