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
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Snackbar.make(binding.root, "갤러리 접근 권한이 허용되어 있습니다.", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                Snackbar.make(binding.root, "갤러리에 접근할 권한이 없습니다.", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

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
    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Snackbar.make(
                    binding.root,
                    "갤러리 접근 권한이 허용되어 있습니다.",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)) {
                    showPermissionContextPopup()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("앱에서 사진을 불러오기 위해 권한이 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_ALBUM
                )
            }
            .setNegativeButton("취소") { _, _ -> }
            .create()
            .show()
    }

    companion object {
        private val PERMISSION_ALBUM = 101
    }

}