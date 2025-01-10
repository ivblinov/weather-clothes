package com.weatherclothes.artist.presentation

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import com.weatherclothes.artist.R
import com.weatherclothes.artist.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animateLoadScreen()
        animateItem(R.animator.animate_sun, binding.sunIV)
        animateItem(R.animator.animate_cloud, binding.cloudIV)
        animateItem(R.animator.animate_weather_tv, binding.weatherTV)
        animateItem(R.animator.animate_clothes_tv, binding.clothesTV)

        val animator = ObjectAnimator.ofFloat(binding.sunIV, "alpha", 1f, 1f)
        animator.duration = 1400

        animator.addListener(
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        )
        animator.start()
    }

    fun animateItem(res: Int, animateView: View) {
        val animator = AnimatorInflater.loadAnimator(
            this,
            res
        ) as AnimatorSet
        animator.setTarget(animateView)
        animator.start()
    }

    fun animateLoadScreen() {
        val sun = binding.sunIV
        val cloud = binding.cloudIV
        val animSun = translateItem(0.0f, 0.3f, 0.0f, 0.0f, 700)
        val animCloud = translateItem(0.0f, -0.25f, 0.0f, 0.0f, 700)
        sun.startAnimation(animSun)
        cloud.startAnimation(animCloud)
    }

    fun translateItem(
        startX: Float,
        endX: Float,
        startY: Float,
        endY: Float,
        duration: Long
    ): TranslateAnimation {
        val animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            startX,
            Animation.RELATIVE_TO_SELF,
            endX,
            Animation.RELATIVE_TO_SELF,
            startY,
            Animation.RELATIVE_TO_SELF,
            endY
        )
        animation.duration = duration
        animation.fillAfter = true
        return animation
    }
}