package com.ubcompany.umbba_android.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityOnboardingFinishBinding
import com.ubcompany.umbba_android.presentation.MainActivity
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFinishActivity :
    BindingActivity<ActivityOnboardingFinishBinding>(R.layout.activity_onboarding_finish) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goBack()
        animateFadeBackground()
        goMainActivity()
    }

    private fun goBack() {
        binding.btnBack1.setOnSingleClickListener {
            finish()
        }
        binding.btnBack2.setOnSingleClickListener {
            finish()
        }
    }

    private fun animateFadeBackground() {
        Handler(Looper.getMainLooper()).postDelayed({
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
        }, 1000)

    }

    private fun goMainActivity() {
        binding.btnStart.setOnSingleClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }
}