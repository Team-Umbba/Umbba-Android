package com.sopt.umbba_android.presentation.qna

import android.content.Intent
import android.graphics.BlurMaskFilter
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.sopt.umbba_android.R
import com.sopt.umbba_android.data.model.response.ListQuestionAnswerResponseDto
import com.sopt.umbba_android.data.model.response.QuestionAnswerResponseDto
import com.sopt.umbba_android.databinding.ActivityQuestionAnswerBinding
import com.sopt.umbba_android.presentation.qna.viewmodel.QuestionAnswerViewModel
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingActivity
import com.sopt.umbba_android.util.setOnSingleClickListener
import timber.log.Timber

class QuestionAnswerActivity :
    BindingActivity<ActivityQuestionAnswerBinding>(R.layout.activity_question_answer),
    View.OnClickListener {
    private val viewModel: QuestionAnswerViewModel by viewModels { ViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        binding.vm = viewModel
        observeQnaViewFlag()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_qna_back -> finish()
        }
    }

    private fun observeQnaViewFlag() {
        val qnaId = intent.getLongExtra("questionId", -1)
        Timber.d("qnaId activity에서 $qnaId")
        if (qnaId == -1L) {
            viewModel.getQuestionAnswer()
            viewModel.isBeforeList.value = false
            observeQnaResponse()
        } else {
            viewModel.getListQuestionAnswer(qnaId)
            viewModel.isBeforeList.value = true
            observeListQnaResponse()
        }
    }

    private fun setClickEvent(data: QuestionAnswerResponseDto.QnaData) {
        if (data.isMyAnswer == false) {
            binding.btnAnswer.setOnSingleClickListener {
                Intent(this@QuestionAnswerActivity, AnswerActivity::class.java).apply {
                    putExtra("section", data.section)
                    putExtra("topic", data.topic)
                    putExtra("question", data.myQuestion)
                    putExtra("index", data.index)
                    startActivity(this)
                }
            }
        }
    }

    private fun observeListQnaResponse() {
        viewModel.listQnaResponse.observe(this@QuestionAnswerActivity) {
            setBtnEnable(true)
            setListAnswerText(it)
        }
    }

    private fun observeQnaResponse() {
        viewModel.qnaResponse.observe(this@QuestionAnswerActivity) {
            setAnswerText(it)
            setClickEvent(it)
            setBtnEnable(it.isMyAnswer)
        }
    }

    private fun setListAnswerText(data:ListQuestionAnswerResponseDto.QnaData){
        with(binding){
            tvAnswerOther.text = data.opponentAnswer
            tvAnswerMe.text= data.myAnswer
        }
    }
    private fun setAnswerText(data: QuestionAnswerResponseDto.QnaData) {
        with(binding) {
            if (data.isOpponentAnswer == true) {
                if (data.isMyAnswer == true) {
                    tvAnswerMe.text = data.myAnswer
                    tvAnswerOther.text = data.opponentAnswer
                    setBlurText(false)
                } else {
                    tvAnswerOther.text = data.opponentAnswer
                    tvAnswerMe.text = getString(R.string.answer_me_hint)
                    setBlurText(true)
                }
            } else {
                if (data.isMyAnswer == true) {
                    tvAnswerMe.text = data.myAnswer
                } else {
                    tvAnswerMe.text = getString(R.string.answer_me_hint)
                }
                tvAnswerOther.text = getString(R.string.answer_opponent_hint)
            }
        }
    }

    private fun setBtnEnable(enable: Boolean?) {
        if (enable == true) {
            with(binding) {
                btnAnswer.setTextColor(
                    ContextCompat.getColor(
                        this@QuestionAnswerActivity,
                        R.color.primary_500
                    )
                )
                btnAnswer.background = ContextCompat.getDrawable(
                    this@QuestionAnswerActivity,
                    R.drawable.shape_pri500_btn_stroke_r50_rect
                )
                btnAnswer.text = getString(R.string.home)
                btnAnswer.setOnSingleClickListener {
                    finish()
                }
            }
        }
    }

    private fun setBlurText(isBlur: Boolean) {
        with(binding) {
            tvAnswerOther.setLayerType(View.LAYER_TYPE_SOFTWARE, null).apply {
                if (isBlur) tvAnswerOther.paint.maskFilter =
                    BlurMaskFilter(16f, BlurMaskFilter.Blur.NORMAL)
                else tvAnswerOther.paint.maskFilter = null
            }
        }
    }

    override fun onResume() {
        super.onResume()
        observeQnaViewFlag()
    }
}