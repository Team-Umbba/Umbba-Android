package com.sopt.umbba_android.presentation.setting

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.FragmentSettingBinding
import com.sopt.umbba_android.presentation.onboarding.OnboardingFinishActivity
import com.sopt.umbba_android.util.binding.BindingFragment
import com.sopt.umbba_android.util.fcm.MyFirebaseMessagingService

class SettingFragment : BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeSwitchNotification()
        setSwitchNotification()
        setClickEvent()
    }

    private val notificationSettingsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        // 앱의 설정 화면으로 이동한 후 결과를 처리하는 로직
        Log.e("hyeon","나 지금 돌아왔어요.")
        setSwitchNotification()
    }
    private fun setSwitchNotification() {
        binding.switchNotification.isChecked = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
        Log.e("hyeon", "현재 알림 허용이 되었니? " + binding.switchNotification.isChecked)
    }

    private fun changeSwitchNotification() {
        binding.switchNotification.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked !=  (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED)){
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + requireActivity().packageName))
                notificationSettingsLauncher.launch(intent)
                Log.e("hyeon","나 지금 switch 바꿨어요.")
            }
        }
    }

    private fun setClickEvent() {
        with(binding) {
            clManageAccount.setOnClickListener {
                startActivity(Intent(requireActivity(), ManageAccountActivity::class.java))
            }
            clAboutUmbba.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.notion.so/f1a14bf60ed4421f9b3761ef88906adb")
                    )
                )
            }
            clTos.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("\"https://www.notion.so/f1a14bf60ed4421f9b3761ef88906adb")
                    )
                )
            }
            clNotice.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.notion.so/f1a14bf60ed4421f9b3761ef88906adb")
                    ) //공지사항 바꾸기
                )
            }
        }
    }


}
