package com.sopt.umbba_android.presentation.onboarding

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityNotifyTimeBinding
import com.sopt.umbba_android.util.binding.BindingActivity
import com.sopt.umbba_android.util.setOnSingleClickListener

class NotifyTimeActivity :
    BindingActivity<ActivityNotifyTimeBinding>(R.layout.activity_notify_time),
    View.OnClickListener {

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            // 알림권한 허용 o
            Snackbar.make(binding.root, R.string.allow_notification, Snackbar.LENGTH_SHORT).show()
        } else {
            // 알림권한 허용 x
            Snackbar.make(binding.root, R.string.not_allow_notification, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this

        setTimeText()
        askNotificationPermission()
        goOnboardingFinishActivity()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> finish()
        }
    }

    private fun setTimeText() {
        val time = intent.getStringExtra("time").toString()
        Log.e("yeonjin", "setTime : $time")
        var hour = time.substring(0, 2).toInt()
        val minute = time.substring(3, 5)
        var hourText = ""
        when (hour) {
            1, 2, 3, 4, 5 -> hourText = getString(R.string.dawn)
            6, 7, 8, 9, 10, 11 -> hourText = getString(R.string.morning)
            12, 13, 14, 15, 16, 17 -> hourText = getString(R.string.day)
            18, 19, 20 -> hourText = getString(R.string.evening)
            21, 22, 23, 24 -> hourText = getString(R.string.night)
        }
        if (hour >= 13) { hour %= 12 }
        if (minute == "00") {
            binding.tvTitle.text = getString(R.string.everyday_notification, hourText, hour)
        } else {
            binding.tvTitle.text = getString(R.string.everyday_notification_half, hourText, hour)
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(binding.root, R.string.allowing_notification, Snackbar.LENGTH_SHORT).show()
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // 왜 알림을 허용해야 하는지에 대한 설명 + 권한 거절 시 권한 설정 화면으로 이동
                Snackbar.make(binding.root, R.string.if_allow_notification, Snackbar.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:"+ this.packageName))
                startActivity(intent)
                this.finish()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun goOnboardingFinishActivity() {
        with(binding) {
            btnGoPast.setOnSingleClickListener {
                startActivity(Intent(this@NotifyTimeActivity, OnboardingFinishActivity::class.java))
            }
        }
    }
}