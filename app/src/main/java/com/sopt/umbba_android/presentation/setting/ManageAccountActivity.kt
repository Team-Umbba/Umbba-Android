package com.sopt.umbba_android.presentation.setting

import android.content.Intent
import android.os.Bundle
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityManageAccountBinding
import com.sopt.umbba_android.util.binding.BindingActivity

class ManageAccountActivity: BindingActivity<ActivityManageAccountBinding>(R.layout.activity_manage_account) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setClickEvent()
    }

    private fun setClickEvent(){
        with(binding){
            clLogout.setOnClickListener {
                TODO("서버 로그아웃 API 연결")
            }
            clDeleteAccount.setOnClickListener {
                startActivity(Intent(this@ManageAccountActivity,DeleteAccountActivity::class.java))
            }
        }
    }
}