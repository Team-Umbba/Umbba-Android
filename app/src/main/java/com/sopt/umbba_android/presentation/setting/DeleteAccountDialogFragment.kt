package com.sopt.umbba_android.presentation.setting

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.sopt.umbba_android.databinding.FragemntDeleteAccountDialogBinding
import com.sopt.umbba_android.presentation.setting.viewmodel.DeleteAccountViewModel


class DeleteAccountDialogFragment : DialogFragment() {

    private val deleteAccountViewModel by viewModels<DeleteAccountViewModel>()
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
                deleteAccountViewModel.deleteAccount()
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