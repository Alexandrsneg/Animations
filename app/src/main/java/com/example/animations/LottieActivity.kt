package com.example.animations

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView

class LottieActivity : AppCompatActivity() {

    lateinit var lottieView: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)

        lottieView = findViewById(R.id.lavLottieView)
        lottieView.playAnimation()
        lottieView.addAnimatorListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                Toast.makeText(this@LottieActivity, "animation end", Toast.LENGTH_LONG).show()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
    }
}