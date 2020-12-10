package com.example.lab9

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import pl.droidsonroids.gif.GifDrawable

class Spaceship @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val spaceshipGif = GifDrawable(resources, R.drawable.ignition)

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val spaceshipBmp = spaceshipGif.toBitmap()
        canvas?.drawBitmap(spaceshipBmp, null, calculateRectF(0.7f, 0.65f, spaceshipBmp.width / 2, spaceshipBmp.height / 2), null)
    }

    private fun calculateRectF(left: Float, top: Float, imgWidth: Int, imgHeight: Int) =
        RectF(width * left, height * top, width * left + imgWidth, height * top + imgHeight)

}