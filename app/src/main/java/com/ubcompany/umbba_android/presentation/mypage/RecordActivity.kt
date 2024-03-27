package com.ubcompany.umbba_android.presentation.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityRecordBinding
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordActivity : BindingActivity<ActivityRecordBinding>(R.layout.activity_record),
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

    private fun goUploadActivity() {
        binding.btnUpload.setOnSingleClickListener {
            // 이동
        }
    }
}