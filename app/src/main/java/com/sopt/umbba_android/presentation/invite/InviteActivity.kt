package com.sopt.umbba_android.presentation.invite

import android.content.Intent
import android.os.Bundle
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityInviteBinding
import com.sopt.umbba_android.presentation.onboarding.CommunicationActivity
import com.sopt.umbba_android.util.binding.BindingActivity

class InviteActivity : BindingActivity<ActivityInviteBinding>(R.layout.activity_invite) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goCommunicationActivity()
        goInviteCodeActivity()
    }

    private fun goCommunicationActivity() {
        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, CommunicationActivity::class.java))
        }
    }

    private fun goInviteCodeActivity() {
        binding.btnInputCode.setOnClickListener {
            startActivity(Intent(this, InviteCodeActivity::class.java))
        }
    }
}