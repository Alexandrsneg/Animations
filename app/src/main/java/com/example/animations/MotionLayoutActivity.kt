package com.example.animations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout

class MotionLayoutActivity : AppCompatActivity() {
    lateinit var mlRoot: MotionLayout
    lateinit var btnMotion: Button
     var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)

        mlRoot = findViewById(R.id.mlRoot)
        btnMotion = findViewById(R.id.btnMotion)

        btnMotion.setOnClickListener {
            startAnim()
        }


        mlRoot.addTransitionListener(object: MotionLayout.TransitionListener{
            override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}
            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {}
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (motionLayout?.currentState == R.id.finalSet) {
                    counter++
                    Toast.makeText(this@MotionLayoutActivity, counter.toString(), Toast.LENGTH_SHORT).show()
                    startAnim()
                }
            }
            override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {}
        })
    }

    private fun startAnim(){
        if (counter < 3) {
            mlRoot.setTransition(R.id.transition1)
            mlRoot.transitionToEnd()
        }
    }
}