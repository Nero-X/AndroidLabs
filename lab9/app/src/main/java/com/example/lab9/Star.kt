package com.example.lab9

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import kotlin.random.Random

class Star @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val star = Shape(Color.YELLOW, pathEffect = CornerPathEffect(5f))

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        star.setStar(width * Random.nextDouble(0.05, 0.65).toFloat(), height * Random.nextDouble(0.05, 0.25).toFloat(), 30f, 8f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(star.path, star.paint)
    }

    fun startAnim() {
        val starAnim = AnimationUtils.loadAnimation(context, R.anim.star_anim)
        startAnimation(starAnim)
    }
}