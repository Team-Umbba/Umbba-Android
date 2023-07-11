package com.sopt.umbba_android.presentation.qna

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.FragmentConfirmAnswerDialogBinding

class ConfirmAnswerDialogFragment : DialogFragment() {

    private var _binding: FragmentConfirmAnswerDialogBinding? = null
    private val binding get() = requireNotNull(_binding) { "ConfirmAnswerDialogFragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmAnswerDialogBinding.inflate(inflater, container, false)
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
            btnBack.setOnClickListener {
                dismiss()
            }
            btnConfirm.setOnClickListener {
                dismiss()
                //TODO(답변 내용 POST API 연결 부분)
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