package com.ubcompany.umbba_android.presentation.mypage

import android.content.Context
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
import com.ubcompany.umbba_android.databinding.FragmentDeleteRecordDialogBinding
import com.ubcompany.umbba_android.presentation.mypage.viewmodel.DeleteRecordDialogViewModel
import com.ubcompany.umbba_android.presentation.mypage.viewmodel.RecordViewModel
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteRecordDialogFragment : DialogFragment() {

    private var _binding: FragmentDeleteRecordDialogBinding? = null
    private val binding get() = requireNotNull(_binding) { "DeleteRecordDialogFragment is null" }

    private val viewModel by viewModels<DeleteRecordDialogViewModel>()

    private var onListenerDelete: OnListenerDelete? = null

    interface OnListenerDelete {
        fun onDeleteRecord(status: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onListenerDelete = activity as OnListenerDelete
    }

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
        observeDeleteRecordStatus()
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun observeDeleteRecordStatus() {
        viewModel.deleteResponseStatus.observe(viewLifecycleOwner) { responseStatus ->
            if (responseStatus == SUCCESS_DELETE_RECORD) {
                onListenerDelete?.onDeleteRecord(SUCCESS_DELETE_RECORD)
                dismiss()
            }
        }
    }

    private fun setBtnClickEvent() {
        with(binding) {
            btnCancel.setOnSingleClickListener {
                dismiss()
            }
            btnConfirm.setOnSingleClickListener {
                val albumId = arguments?.getInt("albumId")
                Log.d("yeonjin", "삭제할 record id $albumId")
                if (albumId != null) {
                    viewModel.deleteRecord(albumId)
                }
            }
        }
    }

    private fun backgroundDesign() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onDetach() {
        super.onDetach()
        onListenerDelete = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val SUCCESS_DELETE_RECORD = 200
    }
}