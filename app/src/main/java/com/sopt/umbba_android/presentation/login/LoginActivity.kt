package com.sopt.umbba_android.presentation.login

import android.content.Intent
import android.os.Bundle
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityLoginBinding
import com.sopt.umbba_android.util.binding.BindingActivity

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginWithKaKao()
    }

    private fun loginWithKaKao() {
        //TODO("카카오 로그인 및 자동 로그인 진행")
        goAgreePrivacyUseActivity()
    }

    private fun goAgreePrivacyUseActivity() {
        binding.btnKakaoLogin.setOnClickListener {
            startActivity(Intent(this, AgreePrivacyUseActivity::class.java))
        }
    }
}