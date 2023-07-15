package com.sopt.umbba_android.presentation.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityOnboardingFinishBinding
import com.sopt.umbba_android.util.binding.BindingActivity

class OnboardingFinishActivity : BindingActivity<ActivityOnboardingFinishBinding>(R.layout.activity_onboarding_finish) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        animateFadeBackground()
    }

    private fun animateFadeBackground() {
        val fadeOutAnim = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        with(binding.clStart) {
            startAnimation(fadeOutAnim)
            visibility = View.INVISIBLE
        }
        val fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fadein)
        binding.clArrive.startAnimation(fadeInAnim)
        with(binding.clArrive) {
            startAnimation(fadeInAnim)
            visibility = View.VISIBLE
        }
    }
}