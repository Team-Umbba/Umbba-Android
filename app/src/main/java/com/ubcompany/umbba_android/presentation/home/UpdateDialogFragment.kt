package com.ubcompany.umbba_android.presentation.home

import android.app.Activity.RESULT_OK
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.startUpdateFlowForResult
import com.ubcompany.umbba_android.data.local.SharedPreferences
import com.ubcompany.umbba_android.databinding.FragmentBackAnswerDialogBinding
import com.ubcompany.umbba_android.databinding.FragmentUpdateDialogBinding
import com.ubcompany.umbba_android.presentation.MainActivity
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateDialogFragment() : DialogFragment() {
    private var _binding: FragmentUpdateDialogBinding? = null
    private val binding get() = requireNotNull(_binding) { "UpdateDialogFragment is null" }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode != RESULT_OK) {
                Toast.makeText(requireContext(), "업데이트에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                SharedPreferences.setUpdateAvailableBoolean(IS_UPDATE_AVAILABLE,false)
                dismiss()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backgroundDesign()
        setBtnClickEvent()
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setBtnClickEvent() {
        with(binding) {
            btnClose.setOnSingleClickListener {
                dismiss()
            }
            btnUpdate.setOnSingleClickListener {
                requestUpdate()
            }
        }
    }

    private fun requestUpdate(){
        val appUpdateManager = AppUpdateManagerFactory.create(requireContext())
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(
                    AppUpdateType.IMMEDIATE
                )
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    startForResult,
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                )
            }
        }
    }


    private fun backgroundDesign() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        const val IS_UPDATE_AVAILABLE = "IS_UPDATE_AVAILABLE"
    }
}