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
        //if(abs(phase) != 0.5f)
        //path.arcTo(x, y, x + radius - radius * 0.75f, y + radius, 270f, 180f, false) // -0.75
        //path.arcTo(x + radius * 0.25f, y, x + radius, y + radius, 270f, -180f, false) // -0.25
        //path.arcTo(x, y, x + radius, y + radius, 270f, 180f, false) // 0
        //path.arcTo(x, y, x + radius - radius * 0.25f, y + radius, 270f, 180f, false) // 0.25
        //path.arcTo(x + radius * 0.25f, y, x + radius, y + radius, 270f, -180f, false) // 0.75
        //path.arcTo(x, y, x + radius, y + radius, 270f, 180f * sign(tan(phase * PI)).toFloat(), false) // +-1
        //path.arcTo(x, y, x + radius, y + radius, 270f, 0f, false) // +-0.5
        /*path.arcTo(
            x + radius * min(sin(phase * PI / 2), 0.0).toFloat(), y,
            x + radius + radius * 0.75f * max(sin(phase * PI / 2), 0.0).toFloat(), y + radius,
            270f, 180f * sign(tan(phase * PI).toFloat()), false)*/
        //val l = abs(min(sin(phase * 2 * PI), 0.0)).toFloat()
        //val r = cos(phase * 2 * PI).toFloat()
        /*val l = radius * abs(sin(phase * PI).coerceIn(-1.0, 0.0)).toFloat()
        val r = radius * sin(phase * PI).coerceIn(0.0, 1.0).toFloat()
        val s = sign(tan(phase * PI))
        path.arcTo(x + l, y, x + radius - r, y + radius, 270f, 180f * s, false)*/
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