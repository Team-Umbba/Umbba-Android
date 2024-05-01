package com.ubcompany.umbba_android.presentation.mypage

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityRecordBinding
import com.ubcompany.umbba_android.presentation.mypage.viewmodel.RecordViewModel
import com.ubcompany.umbba_android.util.BitmapUtil
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordActivity : BindingActivity<ActivityRecordBinding>(R.layout.activity_record),
    DeleteRecordDialogFragment.OnListenerDelete, View.OnClickListener {

    private val viewModel by viewModels<RecordViewModel>()
    private lateinit var recordAdapter: RecordAdapter
    private lateinit var bitmapUtil: BitmapUtil

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
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri: Uri? ->
            if (imageUri != null) {
                bitmapUtil.createUriToBitmap(imageUri).let { bitmap ->
                    Log.d("yeonjin", "사진 bitmap $bitmap")
                    Log.d("yeonjin", "사진 url ${viewModel.presignedUrl.value}")
                    viewModel.uploadImage(viewModel.presignedUrl.value.toString(), bitmap)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        bitmapUtil = BitmapUtil(this)

        initAdapter()
        observeRecordData()
        goUploadActivity()

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> finish()
        }
    }

    private fun initAdapter() {
        recordAdapter = RecordAdapter {
            val bundle = Bundle()
            val deleteDialog = DeleteRecordDialogFragment()
            bundle.putInt("albumId", it.id)
            deleteDialog.arguments = bundle
            deleteDialog.show(supportFragmentManager, "DeleteRecordDialogFragment open")

        }
        binding.rvRecord.adapter = recordAdapter
    }

    private fun observeRecordData() {
        viewModel.getRecordListData()
        viewModel.recordListResponse.observe(this) {
            recordAdapter.submitList(it.toList())
            if (viewModel.recordListResponse.value.isNullOrEmpty()) {
                binding.rvRecord.visibility = View.GONE
                binding.ivNotUploadPic.visibility = View.VISIBLE
            } else {
                binding.rvRecord.visibility = View.VISIBLE
                binding.ivNotUploadPic.visibility = View.GONE
            }
        }
    }

    private fun goUploadActivity() {
        binding.btnUpload.setOnSingleClickListener {
            askNotificationPermission()
            viewModel.receivePresignedUrl()
            viewModel.presignedUrl.observe(this) {
                if (it.isNotEmpty()) {
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            }
            viewModel.image.observe(this) {
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
        const val PERMISSION_ALBUM = 101
        const val SUCCESS_DELETE_RECORD = 200
    }

    override fun onResume() {
        super.onResume()
        observeRecordData()
    }

    override fun onDeleteRecord(status: Int) {
        if (status == SUCCESS_DELETE_RECORD) {
            observeRecordData()
        }
    }
}