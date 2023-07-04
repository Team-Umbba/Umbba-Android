package com.sopt.umbba_android.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityGoPastBinding
import com.sopt.umbba_android.util.binding.BindingActivity

class GoPastActivity : BindingActivity<ActivityGoPastBinding>(R.layout.activity_go_past) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setClickButton()
    }

    private fun setClickButton() {
        with(binding) {
            btnGoPast.setOnClickListener {
                startActivity(Intent(this@GoPastActivity, QuestActivity::class.java))
            }
        }
    }
}