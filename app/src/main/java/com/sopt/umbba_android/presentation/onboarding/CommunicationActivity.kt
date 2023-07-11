package com.sopt.umbba_android.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityCommunicationBinding
import com.sopt.umbba_android.util.binding.BindingActivity

class CommunicationActivity : BindingActivity<ActivityCommunicationBinding>(R.layout.activity_communication) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goInputInfoActivity()
    }

    private fun goInputInfoActivity() {
        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, InputInfoActivity::class.java))
        }
    }
}