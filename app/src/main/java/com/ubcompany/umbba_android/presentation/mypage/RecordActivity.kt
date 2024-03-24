package com.ubcompany.umbba_android.presentation.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityRecordBinding
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordActivity : BindingActivity<ActivityRecordBinding>(R.layout.activity_record) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goBack()
    }

    private fun goBack() {
        binding.btnBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun goUploadActivity() {
        binding.btnUpload.setOnSingleClickListener {
            // 이동
        }
    }
}