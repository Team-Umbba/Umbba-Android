package com.ubcompany.umbba_android.presentation.qna

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.data.model.request.AnswerRequestDto
import com.ubcompany.umbba_android.databinding.FragmentConfirmAnswerDialogBinding
import com.ubcompany.umbba_android.presentation.qna.viewmodel.ConfirmAnswerDialogFragmentViewModel
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmAnswerDialogFragment : DialogFragment() {

    private var _binding: FragmentConfirmAnswerDialogBinding? = null
    private val viewModel by viewModels<ConfirmAnswerDialogFragmentViewModel> ()
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
        viewModel.responseStatus.observe(viewLifecycleOwner) {
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
            btnBack.setOnSingleClickListener {
                dismiss()
            }
            btnConfirm.setOnSingleClickListener {
                Snackbar.make(binding.root, R.string.post_answer, Toast.LENGTH_SHORT).show()
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