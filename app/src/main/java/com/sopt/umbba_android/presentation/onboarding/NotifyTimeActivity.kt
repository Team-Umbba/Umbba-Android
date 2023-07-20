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
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityNotifyTimeBinding
import com.sopt.umbba_android.domain.entity.User
import com.sopt.umbba_android.presentation.onboarding.quest.QuestViewModel
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingActivity

class NotifyTimeActivity :
    BindingActivity<ActivityNotifyTimeBinding>(R.layout.activity_notify_time),
    View.OnClickListener {

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            // 알림권한 허용 o
            Snackbar.make(binding.root, "알림 권한이 허용되었습니다.", Snackbar.LENGTH_SHORT).show()
        } else {
            // 알림권한 허용 x
            Snackbar.make(binding.root, "알림 권한이 없습니다.", Snackbar.LENGTH_SHORT).show()
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
            1, 2, 3, 4, 5 -> hourText = "새벽"
            6, 7, 8, 9, 10, 11 -> hourText = "아침"
            12, 13, 14, 15, 16, 17 -> hourText = "낮"
            18, 19, 20 -> hourText = "저녁"
            21, 22, 23, 24 -> hourText = "밤"
        }
        if (hour >= 13) { hour %= 12 }
        if (minute == "00") {
            binding.tvTitle.text = "매일 ${hourText} ${hour}시에\n교신을 보내줄게"
        } else {
            binding.tvTitle.text = "매일 ${hourText} ${hour}시 반에\n교신을 보내줄게"
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(binding.root, "알림 권한이 허용되어 있습니다.", Snackbar.LENGTH_SHORT).show()
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // 왜 알림을 허용해야 하는지에 대한 설명 + 권한 거절 시 권한 설정 화면으로 이동
                Snackbar.make(binding.root, "알림 권한을 설정하면 답변 작성 요청 알림을 받아볼 수 있습니다.", Snackbar.LENGTH_SHORT).show()
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
            btnGoPast.setOnClickListener {
                startActivity(Intent(this@NotifyTimeActivity, OnboardingFinishActivity::class.java))
            }
        }
    }
}