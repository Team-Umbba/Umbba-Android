package com.ubcompany.umbba_android.presentation.mypage

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
import com.ubcompany.umbba_android.databinding.FragmentDeleteRecordDialogBinding
import com.ubcompany.umbba_android.presentation.mypage.viewmodel.RecordViewModel
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteRecordDialogFragment : DialogFragment() {

    private var _binding: FragmentDeleteRecordDialogBinding? = null
    private val binding get() = requireNotNull(_binding) { "DeleteRecordDialogFragment is null" }

    private val viewModel by viewModels<RecordViewModel>()

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
                val bundle = arguments
                var id = 0
                if (bundle != null) {
                    id = bundle.getInt("albumId")
                }
                Log.d("yeonjin", "삭제할 record id $id")
                viewModel.deleteRecord(id)
                viewModel.isImageDelete.value = true
                Log.d("yeonjin fragment", "새로고침 해야하나요 ${viewModel.isImageDelete.value}")
                dismiss()
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