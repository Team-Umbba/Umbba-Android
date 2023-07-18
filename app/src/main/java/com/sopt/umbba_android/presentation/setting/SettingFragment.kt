package com.sopt.umbba_android.presentation.setting

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.FragmentSettingBinding
import com.sopt.umbba_android.util.binding.BindingFragment
import com.sopt.umbba_android.util.fcm.MyFirebaseMessagingService

class SettingFragment : BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickEvent()
        checkNotificationPermission()
        setSwitchNotification()
    }

    private fun setSwitchNotification() {
        binding.switchNotification.setOnCheckedChangeListener { buttonView, isChecked ->
            MyFirebaseMessagingService().isSwitchChecked.value = isChecked
        }
    }

    private fun checkNotificationPermission() {
        MyFirebaseMessagingService().isSwitchChecked.value = (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED)
        binding.switchNotification.isChecked = MyFirebaseMessagingService().isSwitchChecked.value!!
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
                        Uri.parse("https://www.notion.so/f1a14bf60ed4421f9b3761ef88906adb")
                    )
                )
            }
            clNotice.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.notion.so/f1a14bf60ed4421f9b3761ef88906adb")
                    )
                )
            }
        }
    }


}
