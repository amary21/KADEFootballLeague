package com.amary.kade_footballeague.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View


class ViewAnimation {
    fun rotateFab(view: View, rotate: Boolean): Boolean {
        view.animate().setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                }
            })
            .rotation(if (rotate) 135f else 0f)
        return rotate
    }
}