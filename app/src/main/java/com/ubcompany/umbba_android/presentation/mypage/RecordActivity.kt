package com.ubcompany.umbba_android.presentation.mypage

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityRecordBinding
import com.ubcompany.umbba_android.databinding.ItemRecordListBinding
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
                Snackbar.make(binding.root, R.string.allow_gallery, Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                Snackbar.make(binding.root, R.string.not_allow_gallery, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri: Uri? ->
            if (imageUri != null) {
                bitmapUtil.createUriToBitmap(imageUri).let { bitmap ->
                    viewModel.uploadImage(viewModel.presignedUrl.value.toString(), bitmap)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        bitmapUtil = BitmapUtil(this)

        initAdapter()
        touchItemEvent()
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

    private fun touchItemEvent() {
        recordAdapter.initListener(object : RecordAdapter.OnRootClickListener {
            override fun touchRecordItem(isTouched: Boolean, itemBinding: ItemRecordListBinding) {
                if (isTouched) {
                    if (itemBinding.clTitle.visibility == View.VISIBLE) {
                        with(itemBinding) {
                            clTitle.visibility = View.GONE
                            btnTouch.visibility = View.GONE
                            clTouch.visibility = View.VISIBLE
                        }
                    } else {
                        with(itemBinding) {
                            clTitle.visibility = View.VISIBLE
                            btnTouch.visibility = View.VISIBLE
                            clTouch.visibility = View.GONE
                        }
                    }
                }
            }
        })
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
            viewModel.isUrlSaved.observe(this) { isUrlSaved ->
                if (isUrlSaved) {
                    viewModel.initIsUrlSaved()
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            }
            viewModel.isImageSaved.observe(this) { isImageSaved ->
                if (isImageSaved) {
                    viewModel.initIsImageSaved()
                    startActivity(Intent(this, UploadRecordActivity::class.java).apply {
                        putExtra("fileName", viewModel.fileName.value.toString())
                    })
                }
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
                    R.string.allow_gallery,
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
            .setTitle(R.string.need_permission)
            .setMessage(R.string.need_permission_description)
            .setPositiveButton(R.string.agree) { _, _ ->
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_ALBUM
                )
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
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