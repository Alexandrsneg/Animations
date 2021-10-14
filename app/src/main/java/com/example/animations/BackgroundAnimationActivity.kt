package com.example.animations

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class BackgroundAnimationActivity : AppCompatActivity() {
    private lateinit var btnAnim: Button
    private lateinit var btnScale: Button
    private lateinit var btnViewPropsAnim: Button
    var animIsRunning = false
    private var set: AnimatorSet? = null


    private lateinit var mScaleX : ObjectAnimator
    private lateinit var translateAnim : ObjectAnimator
    private lateinit var colorAnim: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background_animation)

        btnAnim = findViewById(R.id.btnAnim)
        btnScale = findViewById(R.id.buttonScale)
        btnViewPropsAnim = findViewById(R.id.buttonViewPropsAnim)
        var animDrawable: AnimationDrawable = btnAnim.background as AnimationDrawable

        mScaleX = ObjectAnimator.ofFloat(btnAnim, "scaleX",2F)

        btnAnim.setOnClickListener {
            if (animIsRunning){
                animDrawable.stop()
                btnAnim.text = "Animation list"
                animIsRunning = false
                mScaleX.pause()
            } else {
                animDrawable.start()
                btnAnim.text = "Stop"
                animIsRunning = true
                mScaleX.apply {
                    duration = 1000
                    repeatCount = ValueAnimator.INFINITE
                    repeatMode = ValueAnimator.REVERSE
                    start()
                }


            }
        }

        btnScale.setOnClickListener {
            translateAnim = ObjectAnimator.ofFloat(btnScale, "translationY", 300F).apply {
                repeatMode = ValueAnimator.REVERSE
                repeatCount = ValueAnimator.INFINITE
            }
            translateAnim.start()
            mScaleX = ObjectAnimator.ofFloat(btnScale, "scaleX",3F).apply {
                repeatMode = ValueAnimator.REVERSE
                repeatCount = ValueAnimator.INFINITE
            }

            colorAnim = ObjectAnimator.ofArgb(btnScale, "textColor", Color.RED, Color.GREEN).apply {
                repeatMode = ValueAnimator.REVERSE
                repeatCount = ValueAnimator.INFINITE
            }

//            if (set == null) {
//               set = AnimatorSet().apply {
//                    playTogether(translateAnim, mScaleX, colorAnim)
//                    duration = 1000
//                }
//            }
//
//            set?.let {
//                if (it.isRunning){
//                    it.end()
//                } else {
//                    it.start()
//                }
//            }
        }

        btnViewPropsAnim.setOnClickListener {
            it.animate()
                .translationY(300F)
                .scaleX(2F)
                .alpha(0.5F)
                .withEndAction {
                    Toast.makeText(this, "Animation Ended", Toast.LENGTH_LONG).show()
                }
                .duration = 2000
        }
    }
}