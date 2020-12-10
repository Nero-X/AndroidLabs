package com.example.lab9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import pl.droidsonroids.gif.AnimationListener
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView
import java.util.*
import kotlin.concurrent.schedule
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var spaceshipAnim = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stars = Array(5) { Star(this).also {
                constraintLayout.addView(it)
                Timer().schedule(Random.nextLong(3000L)) { it.startAnim() }
            }
        }

        val a = AnimationUtils.loadAnimation(this, R.anim.spaceship_anim)

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
                R.drawable.low_speed -> spaceship.startAnimation(a)
            }
        }

        Timer().schedule(3000L) {
            runOnUiThread { spaceship.visibility = View.VISIBLE }
            spaceshipAnim = R.drawable.build
            val gif = spaceship.drawable as GifDrawable
            gif.reset()
        }
    }
}