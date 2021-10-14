package com.example.animations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.*
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class TransitionSceneActivity : AppCompatActivity() {
    lateinit var btnStartTransition: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_scene)

        btnStartTransition = findViewById(R.id.btnStartTransition)
        btnStartTransition.setOnClickListener {
            var fade = Fade()
            var set = TransitionSet()
            var changeBounds = ChangeBounds()

            set
                .addTransition(fade)
                .addTransition(changeBounds)
                .addListener(object: Transition.TransitionListener{
                    override fun onTransitionStart(transition: Transition?) {

                    }

                    override fun onTransitionEnd(transition: Transition?) {
                        Toast.makeText(applicationContext, "Transition Ended", Toast.LENGTH_SHORT).show()
                    }

                    override fun onTransitionCancel(transition: Transition?) {

                    }

                    override fun onTransitionPause(transition: Transition?) {

                    }

                    override fun onTransitionResume(transition: Transition?) {

                    }

                })
                .duration = 2000




            var root: ViewGroup = findViewById(R.id.root)
            var scene2 = Scene.getSceneForLayout(
                root,
                R.layout.activity_transition_scene2,
                applicationContext
            )
            TransitionManager.go(scene2, set)
        }
    }
}