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
import com.sopt.umbba_android.databinding.ActivitySetTimeBinding
import com.sopt.umbba_android.domain.entity.User
import com.sopt.umbba_android.util.binding.BindingActivity
import com.sopt.umbba_android.util.setTimeInterval

class SetTimeActivity : BindingActivity<ActivitySetTimeBinding>(R.layout.activity_set_time),
    View.OnClickListener {

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            // 알림권한 허용 o
            Snackbar.make(binding.root, "알림 권한이 허용되었습니다.", Snackbar.LENGTH_SHORT).show()
            startActivity(Intent(this, OnboardingFinishActivity::class.java))
        } else {
            // 알림권한 허용 x
            Snackbar.make(binding.root, "알림 권한이 없습니다.", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this

        customTimePickerInterval()
        setDefaultTime()
        goOnboardingFinishActivity()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> finish()
        }
    }

    private fun customTimePickerInterval() {
        binding.tpTime.setTimeInterval(30)
    }

    private fun setDefaultTime() {
        binding.tpTime.hour = 23
        binding.tpTime.minute = 0
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
        binding.btnNext.setOnClickListener {
            askNotificationPermission()
            val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("userData", User::class.java)
            } else {
                intent.getParcelableExtra<User>("userData")
            }
            Log.e("yeonjin", "setTime parcelables : $userData")
            val questData = intent.getStringArrayExtra("questData")
            if (questData != null) {
                Log.e("yeonjin", "quest 배열 값 잘 들어옴")
            } else {
                Log.e("yeonjin", "quest 배열 값 안 들어옴")
            }
            //서버 연결
            startActivity(Intent(this, OnboardingFinishActivity::class.java))
        }
    }
}