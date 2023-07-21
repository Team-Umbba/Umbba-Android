package com.sopt.umbba_android.presentation.invite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityInviteBinding
import com.sopt.umbba_android.domain.entity.User
import com.sopt.umbba_android.presentation.onboarding.CommunicationActivity
import com.sopt.umbba_android.util.binding.BindingActivity
import com.sopt.umbba_android.util.setOnSingleClickListener

class InviteActivity : BindingActivity<ActivityInviteBinding>(R.layout.activity_invite) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goCommunicationActivity()
        goInviteCodeActivity()
    }

    private fun goCommunicationActivity() {
        // 초대하는 측
        binding.btnStart.setOnSingleClickListener {
            val userData = User(isReceiver = false)
            Log.e("yeonjin", "invite parcelable : ${userData.isReceiver}")
            startActivity(Intent(this, CommunicationActivity::class.java).apply {
                putExtra("userData", userData)
            })
        }
    }

    private fun goInviteCodeActivity() {
        // 초대받는 측
        binding.btnInputCode.setOnSingleClickListener {
            val userData = User(isReceiver = true)
            Log.e("yeonjin", "invite parcelable : ${userData.isReceiver}")
            startActivity(Intent(this, InviteCodeActivity::class.java).apply {
                putExtra("userData", userData)
            })
        }
    }
}