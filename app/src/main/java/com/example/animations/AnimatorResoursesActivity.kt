package com.example.animations

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class AnimatorResoursesActivity : AppCompatActivity() {

    private lateinit var ivSoccerBall: ImageView;
    private lateinit var animatorSet: AnimatorSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator_resourses)

        ivSoccerBall = findViewById(R.id.ivSoccer)
        ivSoccerBall.setOnClickListener {
            animatorSet = AnimatorInflater.loadAnimator(this, R.animator.my_animator) as AnimatorSet
            animatorSet.apply {
                duration = 2000
                setTarget(ivSoccerBall)
                start()
            }
        }
    }
}