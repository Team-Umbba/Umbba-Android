package com.ubcompany.umbba_android.presentation.invite

import android.content.Intent
import android.os.Bundle
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityInviteBinding
import com.ubcompany.umbba_android.domain.entity.User
import com.ubcompany.umbba_android.presentation.onboarding.CommunicationActivity
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.setOnSingleClickListener

class InviteActivity : BindingActivity<ActivityInviteBinding>(R.layout.activity_invite) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goCommunicationActivity()
        goInviteCodeActivity()
    }

    private fun goCommunicationActivity() {
        binding.btnStart.setOnSingleClickListener {
            val userData = User(isReceiver = false)
            startActivity(Intent(this, CommunicationActivity::class.java).apply {
                putExtra("userData", userData)
            })
        }
    }

    private fun goInviteCodeActivity() {
        binding.btnInputCode.setOnSingleClickListener {
            val userData = User(isReceiver = true)
            startActivity(Intent(this, InviteCodeActivity::class.java).apply {
                putExtra("userData", userData)
            })
        }
    }
}