package com.sopt.umbba_android.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityNotifyTimeBinding
import com.sopt.umbba_android.util.binding.BindingActivity

class NotifyTimeActivity : BindingActivity<ActivityNotifyTimeBinding>(R.layout.activity_notify_time) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setClickButton()
    }

    private fun setClickButton() {
        with(binding) {
            btnGoPast.setOnClickListener {
                startActivity(Intent(this@NotifyTimeActivity, QuestActivity::class.java))
            }
        }
    }
}