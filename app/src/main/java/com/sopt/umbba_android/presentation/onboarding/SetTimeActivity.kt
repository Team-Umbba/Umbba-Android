package com.sopt.umbba_android.presentation.onboarding

import android.os.Bundle
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivitySetTimeBinding
import com.sopt.umbba_android.util.binding.BindingActivity
import com.sopt.umbba_android.util.setTimeInterval

class SetTimeActivity : BindingActivity<ActivitySetTimeBinding>(R.layout.activity_set_time) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        customTimePickerInterval()
        setDefaultTime()
    }

    private fun customTimePickerInterval() {
        binding.tpTime.setTimeInterval(30)
    }

    private fun setDefaultTime() {
        binding.tpTime.hour = 23
        binding.tpTime.minute = 0
    }
}