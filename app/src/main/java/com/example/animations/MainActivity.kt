package com.example.animations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.animation.*
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.*
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair


class MainActivity : AppCompatActivity() {

    private lateinit var group: Group
    private lateinit var start: Button
    private lateinit var root: ConstraintLayout
    private lateinit var placeholder: Placeholder
    private lateinit var btn: Button
    private lateinit var btnToXmlAnimatorActivity: Button
    private lateinit var btnToBackGroundActivity: Button
    private lateinit var btnGotoScene: Button
    private lateinit var btnGoToAuth: Button
    private lateinit var btnSharedElements: Button

    private lateinit var img1: ImageView
    private lateinit var img2: ImageView
    private lateinit var img3: ImageView

    private lateinit var translate: TranslateAnimation
    private lateinit var rotate: RotateAnimation
    private lateinit var scale: ScaleAnimation
    private lateinit var animationSet: AnimationSet


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placeholder)

//        group = findViewById(R.id.group)
//        start = findViewById(R.id.button2)
        root = findViewById(R.id.root)
        img1 = findViewById(R.id.img1)
        img2 = findViewById(R.id.img2)
        img3 = findViewById(R.id.img3)
        placeholder = findViewById(R.id.placeHolder)
        btn = findViewById(R.id.button)
        btnToBackGroundActivity = findViewById(R.id.btnBackGround)
        btnToXmlAnimatorActivity = findViewById(R.id.btnToXmlAnimator)
        btnGotoScene = findViewById(R.id.btnGoToScene)
        btnGoToAuth = findViewById(R.id.btnGoToAuth)
        btnSharedElements = findViewById(R.id.btnSharedElements)


        translate = TranslateAnimation(0F,200F, 0F, 200F)
        rotate = RotateAnimation(0F, 360F)
        rotate.duration = 3000
        scale = ScaleAnimation(0F, 3F, 0F, 3F)

        animationSet = AnimationSet(this, null)
        animationSet.addAnimation(translate)
        animationSet.addAnimation(rotate)
        animationSet.addAnimation(scale)
        animationSet.duration = 3000

        btn.setOnClickListener {
            var animation1 = AnimationUtils.loadAnimation(this, R.anim.animate_one)
            var animation2 = AnimationUtils.loadAnimation(this, R.anim.animate_two)
            var animation3 = AnimationUtils.loadAnimation(this, R.anim.home_work_anim)

            animation1.setAnimationListener(object: Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }

            })

            it.startAnimation(animation1)
            img3.startAnimation(animation2)
            img1.startAnimation(animation3)

            startActivity(Intent(this@MainActivity,SecondActivity::class.java))
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out)


        }


        img1.setOnClickListener {
            anim(it)
        }
        img2.setOnClickListener {
            anim(it)
        }
        img3.setOnClickListener {
            anim(it)
        }

        btnToBackGroundActivity.setOnClickListener {
            startActivity(Intent(this, BackgroundAnimationActivity::class.java))
        }

        btnToXmlAnimatorActivity.setOnClickListener {
            startActivity(Intent(this, AnimatorResoursesActivity::class.java))
        }

        btnGotoScene.setOnClickListener {
            startActivity(Intent(this, TransitionSceneActivity::class.java))
        }

        btnGoToAuth.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
        }

        btnSharedElements.setOnClickListener {
            val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                Pair(img1, "image1"),
                Pair(img2, "image2"),
                Pair(img3, "image3")
            )
            startActivity(Intent(this, SharedElementsActivity::class.java), options.toBundle())
        }



//        start.setOnClickListener {
//            var set = ConstraintSet()
//            set.clone(this, R.layout.second_keyframe)
//            set.applyTo(root)
//            var transition = ChangeBounds()
//            transition.interpolator = OvershootInterpolator()
//            transition.duration = 2000
//            TransitionManager.beginDelayedTransition(root, transition)
//            start.visibility = View.GONE
//            var fade = Fade()
//            fade.duration = 1000
//            TransitionManager.beginDelayedTransition(root, fade)
//            group.visibility = View.VISIBLE
//
//        }

    }

    private fun anim(v: View){
        var trans = ChangeBounds().apply {
            interpolator = BounceInterpolator()
            duration = 1500
        }
        TransitionManager.beginDelayedTransition(root,trans)
        placeholder.setContentId(v.id)

    }
}