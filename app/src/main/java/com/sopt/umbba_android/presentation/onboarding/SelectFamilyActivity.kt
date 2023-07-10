package com.sopt.umbba_android.presentation.onboarding

import android.os.Bundle
import android.view.View
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivitySelectFamilyBinding
import com.sopt.umbba_android.util.binding.BindingActivity

class SelectFamilyActivity :
    BindingActivity<ActivitySelectFamilyBinding>(R.layout.activity_select_family),
    View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> finish()
        }
    }
}