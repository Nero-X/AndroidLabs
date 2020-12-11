package com.example.lab9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import kotlinx.android.synthetic.main.activity_main.*
import pl.droidsonroids.gif.GifDrawable
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var spaceshipAnim = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stars = Array(5) { Star(this).also {
                constraintLayout.addView(it)
                Timer().schedule(Random.nextLong(3000L)) { runOnUiThread { it.startAnim() } }
            }
        }

        val a = AnimationUtils.loadAnimation(this, R.anim.spaceship_anim)
        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {  }

            override fun onAnimationEnd(animation: Animation?) {
                spaceship.visibility = View.INVISIBLE
                resetAnim()
            }

            override fun onAnimationRepeat(animation: Animation?) {  }

        })

        spaceship.setOnClickListener {
            when (spaceshipAnim) {
                R.drawable.build -> {
                    spaceshipAnim = R.drawable.ignition
                    spaceship.setImageResource(spaceshipAnim)

                    val gif = spaceship.drawable as GifDrawable
                    gif.addAnimationListener {
                        when (spaceshipAnim) {
                            R.drawable.ignition -> {
                                spaceshipAnim = R.drawable.low_speed
                                spaceship.setImageResource(spaceshipAnim)
                                gif.reset()
                            }
                        }
                    }
                }
                R.drawable.low_speed -> {
                    spaceshipAnim = R.drawable.high_speed
                    spaceship.setImageResource(spaceshipAnim)
                    spaceship.startAnimation(a)
                }
            }
        }

        resetAnim()

        val clockTurnAnimation = AnimationUtils.loadAnimation(this, R.anim.clock_turn)
        val hourTurnAnimation = AnimationUtils.loadAnimation(this, R.anim.hour_turn)
        clock.startAnimation(clockTurnAnimation)
        hour_hand.startAnimation(hourTurnAnimation)

        val cuckooAnimation = AnimationUtils.loadAnimation(this, R.anim.cuckoo_anim)
        cuckooAnimation.interpolator = BounceInterpolator()
        cuckooAnimation.fillAfter = true
        Timer().scheduleAtFixedRate(12000L, 24000L) { runOnUiThread {
            cuckoo.visibility = View.VISIBLE
            cuckoo.startAnimation(cuckooAnimation)
        }}
    }

    fun resetAnim() {
        Timer().schedule(3000L) {
            spaceshipAnim = R.drawable.build
            runOnUiThread {
                spaceship.setImageResource(spaceshipAnim)
                spaceship.visibility = View.VISIBLE
            }
            val gif = spaceship.drawable as GifDrawable
            gif.reset()
        }
    }
}