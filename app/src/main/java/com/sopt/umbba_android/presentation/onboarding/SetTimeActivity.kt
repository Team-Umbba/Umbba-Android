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
import com.sopt.umbba_android.data.local.SharedPreferences.setOnboardingBoolean
import com.sopt.umbba_android.databinding.ActivitySetTimeBinding
import com.sopt.umbba_android.domain.entity.User
import com.sopt.umbba_android.presentation.login.LoginActivity.Companion.DID_USER_CLEAR_INVITE_CODE
import com.sopt.umbba_android.presentation.login.LoginActivity.Companion.DID_USER_CLEAR_ONBOARD
import com.sopt.umbba_android.presentation.onboarding.viewmodel.SetTimeViewModel
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingActivity
import com.sopt.umbba_android.util.setOnSingleClickListener
import com.sopt.umbba_android.util.setTimeInterval

class SetTimeActivity : BindingActivity<ActivitySetTimeBinding>(R.layout.activity_set_time),
    View.OnClickListener {

    private val viewModel by viewModels<SetTimeViewModel> { ViewModelFactory(this) }
    private var time = ""
    private val questList = mutableListOf<String>()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Snackbar.make(binding.root, R.string.allow_notification, Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, R.string.not_allow_notification, Snackbar.LENGTH_SHORT).show()
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
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Snackbar.make(binding.root, R.string.allowing_notification, Snackbar.LENGTH_SHORT).show()
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                Snackbar.make(
                    binding.root,
                    R.string.if_allow_notification,
                    Snackbar.LENGTH_SHORT
                ).show()
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + this.packageName))
                startActivity(intent)
                this.finish()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun setTime() {
        val hour =
            if (binding.tpTime.hour == 0) {
                "24"
            } else if (binding.tpTime.hour >= 1 || binding.tpTime.hour <= 9){
                "0${binding.tpTime.hour}"
            } else {
                binding.tpTime.hour
            }
        val minute =
            if (binding.tpTime.minute == 0) {
                "00"
            } else {
                "30"
            }
        time = "$hour:$minute"
    }

    private fun setQuestList(questData: Array<String>?) {
        for (i: Int in 0..4) {
            questList.add(questData?.get(i).toString())
        }
    }

    private fun goOnboardingFinishActivity() {
        binding.btnNext.setOnSingleClickListener {
            askNotificationPermission()
            val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("userData", User::class.java)
            } else {
                intent.getParcelableExtra<User>("userData")
            }
            setTime()
            val questData = intent.getStringArrayExtra("questData")
            setQuestList(questData)
            viewModel.setSendInfo(userData, time, questList)
            viewModel.isPostSuccess.observe(this) {
                if (it) {
                    setOnboardingBoolean(DID_USER_CLEAR_ONBOARD, true)
                    setOnboardingBoolean(DID_USER_CLEAR_INVITE_CODE, true)
                    startActivity(Intent(this, OnboardingFinishActivity::class.java))
                } else {
                    Snackbar.make(binding.root, R.string.fail_information_post, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}