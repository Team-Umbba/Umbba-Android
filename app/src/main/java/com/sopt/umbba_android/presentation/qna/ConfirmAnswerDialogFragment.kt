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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.sopt.umbba_android.data.model.request.AnswerRequestDto
import com.sopt.umbba_android.databinding.FragmentConfirmAnswerDialogBinding
import com.sopt.umbba_android.presentation.qna.viewmodel.ConfirmAnswerDialogFragmentViewModel
import com.sopt.umbba_android.util.ViewModelFactory

class ConfirmAnswerDialogFragment : DialogFragment() {

    private var _binding: FragmentConfirmAnswerDialogBinding? = null
    private val answerViewModel: ConfirmAnswerDialogFragmentViewModel by viewModels {
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
        setBackgroundDesign()
        setPreviewAnswer()
        setBtnClickEvent()
    }

    private fun setPreviewAnswer() {
        with(binding) {
            tvAnswer.text = arguments?.getString("answer")
            tvTopic.text = arguments?.getString("topic")
            tvSection.text = arguments?.getString("section")
            tvQuestion.text = arguments?.getString("question")
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
                dismiss()
                Toast.makeText(requireActivity(), "답변이 전송되었습니다.", Toast.LENGTH_SHORT).show()
                answerViewModel.postAnswer(AnswerRequestDto(arguments?.getString("answer")))
                Log.e("hyeon", "answer 값은 = ${arguments?.getString("answer")}")
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