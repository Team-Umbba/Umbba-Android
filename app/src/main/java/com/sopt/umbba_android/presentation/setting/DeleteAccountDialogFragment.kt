package com.sopt.umbba_android.presentation.setting

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.sopt.umbba_android.databinding.FragemntDeleteAccountDialogBinding
import com.sopt.umbba_android.presentation.login.LoginActivity
import com.sopt.umbba_android.presentation.setting.viewmodel.DeleteAccountViewModel
import com.sopt.umbba_android.presentation.setting.viewmodel.ManageAccountViewModel
import com.sopt.umbba_android.util.ViewModelFactory


class DeleteAccountDialogFragment : DialogFragment() {

    private val viewModel: DeleteAccountViewModel by viewModels { ViewModelFactory(requireActivity()) }
    private var _binding: FragemntDeleteAccountDialogBinding? = null
    private var signOutListener: DeleteAccountDialogFragment.OnSignOutListener? = null
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
    }

    private fun backgroundDesign() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setBtnClickEvent() {
        with(binding) {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnConfirm.setOnClickListener {
                viewModel.signout()
                dismiss()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                requireActivity().finish()
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

    fun setOnSignOutListener(listener: DeleteAccountDialogFragment.OnSignOutListener) {
        this.signOutListener = listener
    }

    interface OnSignOutListener {
        fun onSignOutConfirmed()
    }
}