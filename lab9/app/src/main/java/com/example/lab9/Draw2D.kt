package com.example.lab9

import android.content.Context
import android.graphics.*
import android.view.View
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class Draw2D(context: Context) : View(context) {
    private val paint = Paint()
    private var phase = -1f

    init {
        Timer().scheduleAtFixedRate(0, 100L) { invalidate() }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            paint.shader = LinearGradient(0f, 0f, 0f,  height.toFloat(), resources.getColor(R.color.gradient1), resources.getColor(R.color.gradient2), Shader.TileMode.MIRROR)
            canvas.drawPaint(paint)

            val star = Shape(Color.YELLOW, pathEffect = CornerPathEffect(100f))
            star.setStar(width / 2f, height / 2f, 500f, 150f)
            canvas.drawPath(star.path, star.paint)

            val moon = Shape(Color.YELLOW)
            moon.setCrescent(width - 200f, 100f, 150f, phase)
            canvas.drawPath(moon.path, moon.paint)
            phase += 0.01f
            if (phase > 1) phase = -1f
        }
    }
}