package com.example.animations

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView

class LottieActivity : AppCompatActivity() {

    lateinit var lottieView: LottieAnimationView
    lateinit var btnPlay: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)

        lottieView = findViewById(R.id.lavLottieView)
        btnPlay = findViewById(R.id.btnPlay)
        lottieView.progress = 0F
        btnPlay.setOnClickListener {
            Log.d("PROGRESS prog :", lottieView.progress.toString())
            Log.d("PROGRESS speed :", lottieView.speed.toString())
            if (lottieView.progress == 0.0F) {
                lottieView.speed = 1F
                lottieView.playAnimation()
            } else {
                lottieView.speed = -1F
                lottieView.playAnimation()
            }
        }
//        lottieView.playAnimation()
        lottieView.addAnimatorListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {
                btnPlay.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                btnPlay.isEnabled = true
                Toast.makeText(this@LottieActivity, "animation end", Toast.LENGTH_LONG).show()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
    }
}