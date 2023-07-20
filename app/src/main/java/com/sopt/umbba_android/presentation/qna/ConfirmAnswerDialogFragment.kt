package com.sopt.umbba_android.presentation.qna

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.sopt.umbba_android.data.model.request.AnswerRequestDto
import com.sopt.umbba_android.databinding.FragmentConfirmAnswerDialogBinding
import com.sopt.umbba_android.presentation.qna.viewmodel.ConfirmAnswerDialogFragmentViewModel
import com.sopt.umbba_android.util.ViewModelFactory

class ConfirmAnswerDialogFragment : DialogFragment() {

    private var _binding: FragmentConfirmAnswerDialogBinding? = null
    private val viewModel: ConfirmAnswerDialogFragmentViewModel by viewModels {
        ViewModelFactory(
            requireActivity()
        )
    }
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
        viewModel.setDataFromBundle(arguments)
        binding.vm = viewModel
        setBackgroundDesign()
        setBtnClickEvent()
        observeResponseStatus()
    }

    private fun observeResponseStatus() {
        viewModel.responseStatus.observe(this) {
            if (it == 201) {
                dismiss()
                requireActivity().finish()
            }
        }
    }

    private fun setBackgroundDesign() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setBtnClickEvent() {
        with(binding) {
            btnBack.setOnClickListener {
                dismiss()
            }
            btnConfirm.setOnClickListener {
                Snackbar.make(binding.root, "답변이 전송되었습니다.", Toast.LENGTH_SHORT).show()
                viewModel.postAnswer(AnswerRequestDto(arguments?.getString("answer")))
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