package com.ubcompany.umbba_android.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityEndingBinding
import com.ubcompany.umbba_android.presentation.home.viewmodel.EndingViewModel
import com.ubcompany.umbba_android.presentation.setting.ManageAccountActivity
import com.ubcompany.umbba_android.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EndingActivity : BindingActivity<ActivityEndingBinding>(R.layout.activity_ending) {

    private val viewModel by viewModels<EndingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBtnClickListener()
    }

    private fun setBtnClickListener() {
        with(binding) {
            ivClose.setOnClickListener {
                observeResponse()
            }
            btnSurvey.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.ending_survey_uri))
                    )
                )
            }
            tvClosePage.setOnClickListener {
                observeResponse()
            }
        }
    }

    private fun observeResponse() {
        viewModel.patch7DaysAfter()
        viewModel.baseResponse.observe(this) {
            setResult(RESULT_OK)
            finish()
        }
        viewModel.errorCode.observe(this) {
            if (viewModel.errorCode.value == 501) {
                Snackbar.make(binding.root, "남은 질문이 없습니다. 질문을 추가해주세요.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}