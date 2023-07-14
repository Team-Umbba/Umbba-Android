package com.sopt.umbba_android.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivitySetTimeBinding
import com.sopt.umbba_android.util.binding.BindingActivity
import com.sopt.umbba_android.util.setTimeInterval

class SetTimeActivity : BindingActivity<ActivitySetTimeBinding>(R.layout.activity_set_time),
    View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this

        customTimePickerInterval()
        setDefaultTime()
        goOnboardingFinishActivity()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> finish()
        }
    }

    private fun customTimePickerInterval() {
        binding.tpTime.setTimeInterval(30)
    }

    private fun setDefaultTime() {
        binding.tpTime.hour = 23
        binding.tpTime.minute = 0
    }

    private fun goOnboardingFinishActivity() {
        //if 푸시알림 허용 시
        startActivity(Intent(this, OnboardingFinishActivity::class.java))
        // else 푸시알림 비허용 시 못 넘어감
        TODO("푸시알림 권한 체크")
    }
}