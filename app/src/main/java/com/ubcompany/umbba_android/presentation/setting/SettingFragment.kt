package com.ubcompany.umbba_android.presentation.setting

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.FragmentSettingBinding
import com.ubcompany.umbba_android.util.binding.BindingFragment
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import timber.log.Timber

class SettingFragment : BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeSwitchNotification()
        setSwitchNotification()
        setClickEvent()
    }

    private val notificationSettingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            setSwitchNotification()
        }

    private fun setSwitchNotification() {
        binding.switchNotification.isChecked = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
        Timber.e("현재 알림 허용 값 =  " + binding.switchNotification.isChecked)
    }

    private fun changeSwitchNotification() {
        binding.switchNotification.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked != (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED)
            ) {
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + requireActivity().packageName))
                notificationSettingsLauncher.launch(intent)
            }
        }
    }

    private fun setClickEvent() {
        with(binding) {
            clManageAccount.setOnSingleClickListener {
                startActivity(Intent(requireActivity(), ManageAccountActivity::class.java))
            }
            clAboutUmbba.setOnSingleClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.notion_about_umbba))
                    )
                )
            }
            clTos.setOnSingleClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.notion_tos))
                    )
                )
            }
            clPrivacyNotice.setOnSingleClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.notion_privacy_notice))
                    )
                )
            }
        }
    }


}
