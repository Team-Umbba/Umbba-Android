package com.ubcompany.umbba_android.presentation.mypage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.FragmentBackAnswerDialogBinding
import com.ubcompany.umbba_android.databinding.FragmentBackRecordDialogBinding
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BackRecordDialogFragment : DialogFragment() {

    private var _binding: FragmentBackRecordDialogBinding? = null
    private val binding get() = requireNotNull(_binding) { "BackRecordDialogFragment is null"}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBackRecordDialogBinding.inflate(inflater, container, false)
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
            btnCancel.setOnSingleClickListener {
                dismiss()
            }
            btnConfirm.setOnSingleClickListener {
                dismiss()
                requireActivity().finish()
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

}