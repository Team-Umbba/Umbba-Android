package com.sopt.umbba_android.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityEndingBinding
import com.sopt.umbba_android.presentation.setting.ManageAccountActivity
import com.sopt.umbba_android.util.binding.BindingActivity

class EndingActivity : BindingActivity<ActivityEndingBinding>(R.layout.activity_ending) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBtnClickListener()
    }

    private fun setBtnClickListener() {
        with(binding) {
            ivClose.setOnClickListener {
                setResult(RESULT_OK)
                finish()
            }
            btnSurvey.setOnClickListener {
                TODO("설문조사 링크 넣기")
            }
            btnDeleteAccount.setOnClickListener {
                startActivity(Intent(this@EndingActivity, ManageAccountActivity::class.java))
            }
        }
    }
}