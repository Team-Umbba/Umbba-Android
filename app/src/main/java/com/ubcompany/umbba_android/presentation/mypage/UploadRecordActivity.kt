package com.ubcompany.umbba_android.presentation.mypage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityUploadRecordBinding
import com.ubcompany.umbba_android.presentation.mypage.viewmodel.UploadRecordViewModel
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.hideKeyboard
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadRecordActivity :
    BindingActivity<ActivityUploadRecordBinding>(R.layout.activity_upload_record),
    View.OnClickListener {

    private val viewModel by viewModels<UploadRecordViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this

        validateTitle()
        checkAllInfoComplete()
        showBackDialog()
        uploadRecord()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> finish()
        }
    }

    private fun validateTitle() {
        with(binding) {
            etRecordTitle.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (etRecordTitle.text.toString().length > 15) {
                        layoutRecordTitle.error = getString(R.string.input_error_max_fifteen)
                    } else {
                        layoutRecordTitle.error = null
                    }
                }
                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun checkAllInfoComplete() {
        viewModel.title.observe(this) {
            viewModel.checkInfoComplete()
        }
        viewModel.description.observe(this) {
            viewModel.checkInfoComplete()
        }
        viewModel.isAllInfoComplete.observe(this) {
            with(binding) {
                btnUpload.isEnabled = it
            }
        }
    }

    private fun showBackDialog() {
        BackRecordDialogFragment().show(supportFragmentManager, "BackRecordDialog")
    }

    private fun uploadRecord() {
        binding.btnUpload.setOnSingleClickListener {
            // 사진 전송 및 기록하기 화면으로 이동
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.currentFocus?.let { hideKeyboard(it) }
        with(binding) {
            etRecordTitle.clearFocus()
            etRecordIntroduce.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}