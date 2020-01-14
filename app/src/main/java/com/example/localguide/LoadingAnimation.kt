package com.example.localguide

import android.app.Activity
import android.widget.RelativeLayout
import com.airbnb.lottie.LottieAnimationView

class LoadingAnimation constructor(private val context: Activity, animationName: String) {
    private var lottieAnimationView : LottieAnimationView = LottieAnimationView(context)

    private var rLayout : RelativeLayout = RelativeLayout(context)
    private var rLayoutParams : RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)

    private val lLayoutParams : RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)

    init {
        lottieAnimationView.setAnimation(animationName)
        lottieAnimationView.layoutParams = lLayoutParams

        rLayout.addView(lottieAnimationView)
    }

    fun playAnimation(loop: Boolean) {
        lottieAnimationView.loop(loop)
        context.setContentView(rLayout, rLayoutParams)
        lottieAnimationView.playAnimation()
    }

    fun stopAnimation(originalView: Int) {
        lottieAnimationView.cancelAnimation()
        context.setContentView(originalView)
    }
}
