package com.sopt.umbba_android.presentation.qna

import android.graphics.BlurMaskFilter
import android.os.Bundle
import android.view.View
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityQuestionAnswerBinding
import com.sopt.umbba_android.util.binding.BindingActivity


class QuestionAnswerActivity :
    BindingActivity<ActivityQuestionAnswerBinding>(R.layout.activity_question_answer),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener=this
        setBlurText(true)
        with(binding) {
            btnAnswer.setOnClickListener {
                setBlurText(false) // 임시 블러 테스트
            }
        }
    }

    override fun onClick(view: View?) {
       when(view?.id){
           R.id.iv_qna_back->finish()
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