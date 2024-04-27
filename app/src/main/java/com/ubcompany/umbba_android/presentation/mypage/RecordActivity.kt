package com.ubcompany.umbba_android.presentation.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityRecordBinding
import com.ubcompany.umbba_android.presentation.mypage.viewmodel.RecordViewModel
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordActivity : BindingActivity<ActivityRecordBinding>(R.layout.activity_record),
    View.OnClickListener {

    private val viewModel by viewModels<RecordViewModel>()
    private val launcher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUrl: Uri? ->
            // 서버에 put으로 파일 넘기기
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this

        goUploadActivity()

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> finish()
        }
    }

    private fun goUploadActivity() {
        binding.btnUpload.setOnSingleClickListener {
            viewModel.receivePresignedUrl()
            viewModel.presignedUrl.observe(this) {
                if (it.isNotEmpty()) {
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    startActivity(Intent(this, UploadRecordActivity::class.java).apply {
                        putExtra("fileName", viewModel.fileName.value.toString())
                    })
                }
            }
        }
    }
}