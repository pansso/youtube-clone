package com.youtubeclone.designsystem

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils

fun fadeIn(view: View, duration: Long = 1000) {
    val animation = AlphaAnimation(0f, 1f)
    animation.duration = duration
    animation.fillAfter = true
    view.startAnimation(animation)
    view.visibility = View.VISIBLE
}

fun fadeOut(view: View, duration: Long = 1000) {
    val animation = AlphaAnimation(1f, 0f)
    animation.duration = duration
    animation.fillAfter = true
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
        }
        override fun onAnimationEnd(animation: Animation) {
            view.visibility = View.INVISIBLE
        }
        override fun onAnimationRepeat(animation: Animation) {
        }
    })
    view.startAnimation(animation)
}