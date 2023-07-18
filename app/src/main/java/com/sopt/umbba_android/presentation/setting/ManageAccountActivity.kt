package com.sopt.umbba_android.presentation.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.sopt.umbba_android.R
import com.sopt.umbba_android.data.local.SharedPreferences
import com.sopt.umbba_android.databinding.ActivityManageAccountBinding
import com.sopt.umbba_android.presentation.login.LoginActivity
import com.sopt.umbba_android.presentation.setting.viewmodel.ManageAccountViewModel
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingActivity

class ManageAccountActivity :
    BindingActivity<ActivityManageAccountBinding>(R.layout.activity_manage_account),
    View.OnClickListener {
    private val viewModel: ManageAccountViewModel by viewModels { ViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        setClickEvent()
        observeResponseStatus()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> finish()
        }
    }

    private fun observeResponseStatus() {
        viewModel.responseStatus.observe(this@ManageAccountActivity) {
            if (it == 200) {
                SharedPreferences.clearForLogout()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    private fun setClickEvent() {
        with(binding) {
            clLogout.setOnClickListener {
                viewModel.logout()
            }
            clDeleteAccount.setOnClickListener {
                startActivity(Intent(this@ManageAccountActivity, DeleteAccountActivity::class.java))
            }
        }
    }
}