package com.sopt.umbba_android.presentation.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.FragmentInviteCodeDialogBinding

class InviteCodeDialogFragment : DialogFragment() {

    private var _binding: FragmentInviteCodeDialogBinding? = null
    private val binding get() = requireNotNull(_binding) { "InviteCodeDialogFragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInviteCodeDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        copyInviteCode()
        closeDialog()
        setBackgroundDesign()
    }

    private fun closeDialog() {
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    private fun setBackgroundDesign() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun copyInviteCode() {
        binding.tvInviteCode.setOnClickListener {
            val clipboard: ClipboardManager = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label",binding.tvInviteCode.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireActivity(),"초대 코드가 복사되었습니다",Toast.LENGTH_SHORT).show()
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