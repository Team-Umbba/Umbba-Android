package com.sopt.umbba_android.presentation.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityManageAccountBinding
import com.sopt.umbba_android.presentation.setting.viewmodel.ManageAccountViewModel
import com.sopt.umbba_android.util.binding.BindingActivity

class ManageAccountActivity :
    BindingActivity<ActivityManageAccountBinding>(R.layout.activity_manage_account),
    View.OnClickListener {
    private val manageAccountViewModel by viewModels<ManageAccountViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        setClickEvent()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> finish()
        }
    }

    private fun setClickEvent() {
        with(binding) {
            clLogout.setOnClickListener {
                manageAccountViewModel.logout()
            }
            clDeleteAccount.setOnClickListener {
                startActivity(Intent(this@ManageAccountActivity, DeleteAccountActivity::class.java))
            }
        }
    }
}