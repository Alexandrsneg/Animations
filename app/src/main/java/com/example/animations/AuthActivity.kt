package com.example.animations

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Scene
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button


private const val MAIN = "MAIN"
private const val LOGIN = "LOGIN"
private const val SIGNUP = "SIGNUP"

class AuthActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button

    private lateinit var mainScene: Scene
    private lateinit var loginScene: Scene
    private lateinit var signUpScene: Scene

    private lateinit var transitionSet: TransitionSet
    private lateinit var transitionMgr: TransitionManager

    private lateinit var root: ViewGroup

    private var context: Context = this

    private var currentLayout: String = MAIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)

        root = findViewById(R.id.root)
        mainScene = Scene.getSceneForLayout(root, R.layout.activity_auth, context)
        loginScene = Scene.getSceneForLayout(root, R.layout.layout_login, context)
        signUpScene = Scene.getSceneForLayout(root, R.layout.layout_sign_up, context)

        btnLogin.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)

        transitionSet = TransitionInflater.from(context).inflateTransition(R.transition.mytransitions) as TransitionSet
        transitionMgr = TransitionInflater.from(context).inflateTransitionManager(R.transition.transition_mgr, root)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> {
                currentLayout = LOGIN
                transitionMgr.transitionTo(loginScene)
            }
            R.id.btnSignUp -> {
                currentLayout = SIGNUP
                transitionMgr.transitionTo(signUpScene)
            }
        }
    }

    override fun onBackPressed() {
        if (currentLayout == MAIN)
        super.onBackPressed()
        else {
            transitionMgr.transitionTo(mainScene)
            currentLayout = MAIN

            findViewById<Button>(R.id.btnLogin).setOnClickListener(this)
            findViewById<Button>(R.id.btnSignUp).setOnClickListener(this)
        }
    }
}