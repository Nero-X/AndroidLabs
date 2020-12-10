package com.example.lab9

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.graphics.scale
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.random.Random

class Draw2D(context: Context) : View(context) {
    private val paint = Paint()
    private var phase = -1f
    private lateinit var bg: LinearGradient
    private lateinit var stars: Array<Shape>
    private val starCount = 5
    private val spaceship = BitmapFactory.decodeResource(resources, R.drawable.ignition)
    private val moon = Shape(Color.YELLOW)

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        bg = LinearGradient(0f, 0f, 0f,  height.toFloat(), resources.getColor(R.color.gradient1), resources.getColor(R.color.gradient2), Shader.TileMode.MIRROR)
        stars = Array(starCount) {
            val star = Shape(Color.YELLOW, pathEffect = CornerPathEffect(5f))
            star.setStar(width * Random.nextDouble(0.05, 0.65).toFloat(), height * Random.nextDouble(0.05, 0.25).toFloat(), 30f, 8f)
            return@Array star
        }
    }

    init {
        Timer().scheduleAtFixedRate(0, 100L) { invalidate() }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            paint.shader = bg
            canvas.drawPaint(paint)

            paint.shader = null
            paint.color = resources.getColor(R.color.ground)
            canvas.drawRect(0f, height * 0.8f, width.toFloat(), height.toFloat(), paint)

            for (star in stars) {
                canvas.drawPath(star.path, star.paint)
            }

            moon.setCrescent(width - 200f, 100f, 150f, phase)
            canvas.drawPath(moon.path, moon.paint)
            phase += 0.01f
            if (phase > 1) phase = -1f

            canvas.drawBitmap(spaceship, null, calculateRectF(0.7f, 0.65f, spaceship.width / 2, spaceship.height / 2), null)
        }
    }

    private fun calculateRectF(left: Float, top: Float, imgWidth: Int, imgHeight: Int) =
        RectF(width * left, height * top, width * left + imgWidth, height * top + imgHeight)
}