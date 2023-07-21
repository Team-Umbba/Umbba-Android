package com.sopt.umbba_android.presentation.onboarding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityCommunicationBinding
import com.sopt.umbba_android.domain.entity.User
import com.sopt.umbba_android.util.binding.BindingActivity
import com.sopt.umbba_android.util.setOnSingleClickListener

class CommunicationActivity : BindingActivity<ActivityCommunicationBinding>(R.layout.activity_communication) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goInputInfoActivity()
    }

    private fun goInputInfoActivity() {
        binding.btnStart.setOnSingleClickListener {
            val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("userData", User::class.java)
            } else {
                intent.getParcelableExtra<User>("userData")
            }
            startActivity(Intent(this, InputInfoActivity::class.java).apply {
                putExtra("userData", userData)
            })
        }
    }
}