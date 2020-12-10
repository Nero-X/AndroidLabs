package com.example.lab9

import android.graphics.*
import kotlin.math.*

class Shape(
    primaryColor: Int,
    style: Paint.Style = Paint.Style.FILL,
    secondaryColor: Int = Color.BLACK,
    strokeWidth: Float = 3f,
    pathEffect: PathEffect? = null,
    antiAlias: Boolean = true,
) {
    var paint = Paint()
    var path = Path()

    init {
        paint.color = primaryColor
        paint.strokeWidth = strokeWidth
        paint.style = style
        paint.pathEffect = pathEffect
        paint.isAntiAlias = antiAlias
    }

    fun setCircle(x: Float, y: Float, radius: Float) {
        path.reset()
        path.addCircle(x, y, radius, Path.Direction.CW)
    }

    fun setCrescent(x: Float, y: Float, diameter: Float, phase: Float) {
        path.reset()
        path.arcTo(x, y, x + diameter, y + diameter, 90f, 180f * if (sign(phase) < 0f) -1f else 1f, false)
        val offset = diameter * (1 - abs(2 * abs(phase) - 1)) / 2
        val s = sign(tan(phase * PI)).toFloat()
        path.arcTo(x + offset, y, x + diameter - offset, y + diameter, 270f, 180f * s, false)
    }

    fun setStar(x: Float, y: Float, radius: Float, innerRadius: Float, numOfPt: Int = 4) {
        val section = 2.0 * Math.PI / numOfPt
        path.reset()
        path.moveTo((x + radius * cos(0.0)).toFloat(), (y + radius * sin(0.0)).toFloat())
        path.lineTo(
            (x + innerRadius * cos(0 + section / 2.0)).toFloat(),
            (y + innerRadius * sin(0 + section / 2.0)).toFloat())
        for (i in 1 until numOfPt) {
            path.lineTo(
                (x + radius * cos(section * i)).toFloat(),
                (y + radius * sin(section * i)).toFloat())
            path.lineTo(
                (x + innerRadius * cos(section * i + section / 2.0)).toFloat(),
                (y + innerRadius * sin(section * i + section / 2.0)).toFloat())
        }
        path.close()
    }
}