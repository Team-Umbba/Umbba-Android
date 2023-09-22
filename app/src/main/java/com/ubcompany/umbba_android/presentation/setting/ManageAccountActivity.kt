package com.ubcompany.umbba_android.presentation.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.kakao.sdk.user.UserApiClient
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.data.local.SharedPreferences
import com.ubcompany.umbba_android.databinding.ActivityManageAccountBinding
import com.ubcompany.umbba_android.presentation.login.LoginActivity
import com.ubcompany.umbba_android.presentation.setting.viewmodel.ManageAccountViewModel
import com.ubcompany.umbba_android.util.ViewModelFactory
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.setOnSingleClickListener

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
                logoutForKakao()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    private fun logoutForKakao() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("logout for Kakao", "로그아웃 실패, 토큰 삭제", error)
            } else {
                Log.d("logout for Kakao", "로그아웃 성공, 토큰 삭제")
            }
        }
    }

    private fun setClickEvent() {
        with(binding) {
            clLogout.setOnSingleClickListener {
                viewModel.logout()
            }
            clDeleteAccount.setOnSingleClickListener {
                startActivity(Intent(this@ManageAccountActivity, DeleteAccountActivity::class.java))
            }
        }
    }
}