package com.ubcompany.umbba_android.presentation.onboarding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityCommunicationBinding
import com.ubcompany.umbba_android.domain.entity.User
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunicationActivity :
    BindingActivity<ActivityCommunicationBinding>(R.layout.activity_communication) {
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