package com.sopt.umbba_android.presentation.qna

import android.content.Intent
import android.graphics.BlurMaskFilter
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.sopt.umbba_android.R
import com.sopt.umbba_android.data.model.response.QuestionAnswerResponseDto
import com.sopt.umbba_android.databinding.ActivityQuestionAnswerBinding
import com.sopt.umbba_android.presentation.home.InviteCodeDialogFragment
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingActivity

class QuestionAnswerActivity :
    BindingActivity<ActivityQuestionAnswerBinding>(R.layout.activity_question_answer),
    View.OnClickListener {
    private val viewModel: QuestionAnswerViewModel by viewModels { ViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        showInviteDialog()
        // viewModel.getQuestionAnswer()
        // observeQnaResponse()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_qna_back -> finish()
        }
    }

    private fun showInviteDialog() {
        InviteCodeDialogFragment().show(supportFragmentManager, "InviteCodeDialogFragment")
    }

    private fun showNoOpponentDialog() {
        NoOpponentDialogFragment().show(supportFragmentManager, "NoOpponentDialogFragment")
    }

    private fun setClickEvent(data: QuestionAnswerResponseDto.QnaData) {
        if (data.isMyAnswer == true) {
            binding.btnAnswer.setOnClickListener {
                finish()
            }
        } else {
            binding.btnAnswer.setOnClickListener {
                Intent(this@QuestionAnswerActivity, AnswerActivity::class.java).apply {
                    putExtra("section", data.section)
                    putExtra("topic", data.topic)
                    putExtra("question", data.myQuestion)
                    startActivity(this)
                }
            }
        }
    }

    private fun observeQnaResponse() {
        viewModel.qnaResponse.observe(this@QuestionAnswerActivity) {
            when (it.responseCase) {
                1 -> {
                    setData(it)
                    setAnswerText(it)
                    setClickEvent(it)
                    setBtnEnable(it.isMyAnswer)
                }

                2 -> {
                    finish()
                    showInviteDialog()
                }

                3 -> {
                    finish()
                    showNoOpponentDialog()
                }
            }
        }
    }

    private fun setData(data: QuestionAnswerResponseDto.QnaData) {
        with(binding) {
            layoutAppbar.titleText = data.section
            tvTitle.text = data.topic
            tvQuestionMe.text = data.myQuestion
            tvQuestionOther.text = data.opponentQuestion
            tvFromOther.text = data.opponentUsername
            tvFromMe.text = data.myUsername
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
                    tvAnswerMe.text = "하단 버튼을 눌러 답변을 입력하세요"
                    setBlurText(true)
                }
            } else {
                if (data.isMyAnswer == true) {
                    tvAnswerMe.text = data.myAnswer
                } else {
                    tvAnswerMe.text = "하단 버튼을 눌러 답변을 입력하세요"
                }
                tvAnswerOther.text = "상대방은 아직 답변하지 않았어요"
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
                btnAnswer.text = "홈으로"
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