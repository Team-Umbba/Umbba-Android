package com.ubcompany.umbba_android.presentation.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.ubcompany.umbba_android.databinding.FragmentBackAnswerDialogBinding
import com.ubcompany.umbba_android.databinding.FragmentUpdateDialogBinding
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateDialogFragment : DialogFragment() {


    private var _binding: FragmentUpdateDialogBinding? = null
    private val binding get() = requireNotNull(_binding) { "UpdateDialogFragment is null" }

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
                // TODO(업데이트 클릭 시 구글 플레이스토어로 이동)
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