package com.sopt.umbba_android.presentation.qna

import android.content.Intent
import android.graphics.BlurMaskFilter
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.sopt.umbba_android.R
import com.sopt.umbba_android.data.model.response.QuestionAnswerResponseDto
import com.sopt.umbba_android.databinding.ActivityQuestionAnswerBinding
import com.sopt.umbba_android.util.binding.BindingActivity


class QuestionAnswerActivity :
    BindingActivity<ActivityQuestionAnswerBinding>(R.layout.activity_question_answer),
    View.OnClickListener {
    private val questionAnswerViewModel by viewModels<QuestionAnswerViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        setClickEvent()
        observeQnaResponse()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_qna_back -> finish()
        }
    }

    private fun setClickEvent() {
        with(binding) {
            btnAnswer.setOnClickListener {
                Intent(this@QuestionAnswerActivity, AnswerActivity::class.java).apply {
                    putExtra("section", binding.titleText.toString())
                    putExtra("topic",binding.layoutAppbar.titleText.toString())
                    startActivity(this)
                }
            }
        }
    }

    private fun observeQnaResponse() {
        questionAnswerViewModel.qnaResponse.observe(this@QuestionAnswerActivity) {
            setData(it)
            setAnswerText(it)
        }
    }

    private fun setData(data: QuestionAnswerResponseDto) {
        with(binding) {
            layoutAppbar.titleText = data.topic
            tvTitle.text = data.section
            tvQuestionMe.text = data.myQuestion
            tvQuestionOther.text = data.opponentQuestion
        }
    }

    private fun setAnswerText(data: QuestionAnswerResponseDto) {
        with(binding) {
            if (data.isOpponentAnswer) {
                if (data.isMyAnswer) {
                    tvAnswerMe.text = data.myAnswer
                    tvAnswerOther.text = data.opponentAnswer
                } else {
                    tvAnswerOther.text = data.opponentAnswer
                    tvAnswerMe.text = "답변을 입력해 주세요"
                    setBlurText(true)
                }
            } else {
                if (data.isMyAnswer) {
                    tvAnswerMe.text = data.myAnswer
                    tvAnswerOther.text = "상대방은 아직 답변하지 않았어요"
                }
            }
        }
    }

    private fun setBlurText(isBlur: Boolean) {
        with(binding) {
            tvAnswerOther.setLayerType(View.LAYER_TYPE_SOFTWARE, null).apply {
                if (isBlur) tvAnswerOther.paint.maskFilter =
                    BlurMaskFilter(13f, BlurMaskFilter.Blur.NORMAL)
                else tvAnswerOther.paint.maskFilter = null
            }
        }
    }
}