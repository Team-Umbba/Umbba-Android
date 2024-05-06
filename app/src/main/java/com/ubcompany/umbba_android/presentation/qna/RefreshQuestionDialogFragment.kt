package com.ubcompany.umbba_android.presentation.qna

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.ubcompany.umbba_android.data.model.request.RefreshQuestionRequestDto
import com.ubcompany.umbba_android.databinding.FragmentRefreshQuestionDialogBinding
import com.ubcompany.umbba_android.presentation.qna.viewmodel.RefreshQuestionDialogFragmentViewModel
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RefreshQuestionDialogFragment : DialogFragment() {

    private var _binding: FragmentRefreshQuestionDialogBinding? = null
    private val binding get() = requireNotNull(_binding) { "RefreshQuestionDialogFragment is null" }
    private val viewModel by viewModels<RefreshQuestionDialogFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRefreshQuestionDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setDataFromBundle(arguments)
        binding.vm = viewModel
        setBackgroundDesign()
        setBtnClickEvent()
        observePatchQuestionResponseStatus()
    }

    private fun observePatchQuestionResponseStatus() {
        viewModel.responseStatus.observe(viewLifecycleOwner) { responseStatus ->
            if (responseStatus == SUCCESS_PATCH_QUESTION) {
                dismiss()
                requireActivity().finish()
                startActivity(Intent(requireActivity(), QuestionAnswerActivity::class.java))
            }
        }
    }

    private fun setBackgroundDesign() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setBtnClickEvent() {
        with(binding) {
            btnClose.setOnSingleClickListener {
                dismiss()
            }
            btnRefreshQuestion.setOnSingleClickListener {
                viewModel.patchRefreshQuestion(
                    RefreshQuestionRequestDto(
                        requireArguments().getLong(
                            "questionId"
                        )
                    )
                )
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

    companion object {
        const val SUCCESS_PATCH_QUESTION = 200
    }
}