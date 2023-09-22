package com.ubcompany.umbba_android.presentation.setting

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.kakao.sdk.user.UserApiClient
import com.ubcompany.umbba_android.data.local.SharedPreferences
import com.ubcompany.umbba_android.databinding.FragemntDeleteAccountDialogBinding
import com.ubcompany.umbba_android.presentation.login.LoginActivity
import com.ubcompany.umbba_android.presentation.setting.viewmodel.DeleteAccountViewModel
import com.ubcompany.umbba_android.util.ViewModelFactory
import com.ubcompany.umbba_android.util.setOnSingleClickListener

class DeleteAccountDialogFragment : DialogFragment() {

    private val viewModel: DeleteAccountViewModel by viewModels { ViewModelFactory(requireActivity()) }
    private var _binding: FragemntDeleteAccountDialogBinding? = null
    private val binding get() = requireNotNull(_binding) { "DeleteAccountDialogFragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragemntDeleteAccountDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backgroundDesign()
        setBtnClickEvent()
        observeResponseStatus()
    }

    private fun backgroundDesign() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setBtnClickEvent() {
        with(binding) {
            btnCancel.setOnSingleClickListener {
                dismiss()
            }
            btnConfirm.setOnSingleClickListener {
                viewModel.signout()
            }
        }
    }

    private fun observeResponseStatus() {
        viewModel.responseStatus.observe(this) {
            if (it == 200) {
                SharedPreferences.clearForSignout()
                unlinkForKakao()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    private fun unlinkForKakao() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e("Unlink for Kakao", "연결 끊기 실패", error)
            } else {
                Log.d("Unlink for Kakao", "연결 끊기 성공")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}