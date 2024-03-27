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
import com.ubcompany.umbba_android.databinding.FragmentDeleteRecordDialogBinding
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteRecordDialogFragment : DialogFragment() {

    private var _binding: FragmentDeleteRecordDialogBinding? = null
    private val binding get() = requireNotNull(_binding) { "DeleteRecordDialogFragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteRecordDialogBinding.inflate(inflater, container, false)
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
                // 삭제 서버 연결
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