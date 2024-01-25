package com.ubcompany.umbba_android.presentation.qna

import android.content.Intent
import android.graphics.BlurMaskFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.data.model.response.ListQuestionAnswerResponseDto
import com.ubcompany.umbba_android.data.model.response.QuestionAnswerResponseDto
import com.ubcompany.umbba_android.databinding.ActivityQuestionAnswerBinding
import com.ubcompany.umbba_android.presentation.MainActivity
import com.ubcompany.umbba_android.presentation.qna.viewmodel.QuestionAnswerViewModel
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class QuestionAnswerActivity :
    BindingActivity<ActivityQuestionAnswerBinding>(R.layout.activity_question_answer),
    View.OnClickListener {
    private val viewModel by viewModels<QuestionAnswerViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        binding.vm = viewModel
        observeQnaViewFlag()
        initLoadingGif()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_qna_back -> finish()
        }
    }

    private fun observeQnaViewFlag() {
        val qnaId = intent.getLongExtra("questionId", -1)
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
            var intent = Intent(this@QuestionAnswerActivity, AnswerActivity::class.java).apply {
                putExtra("section", data.section)
                putExtra("topic", data.topic)
                putExtra("question", data.myQuestion)
                putExtra("index", data.index)
            }
            binding.btnAnswer.setOnSingleClickListener {
                startActivity(intent)
            }
            binding.tvAnswerMe.setOnSingleClickListener {
                startActivity(intent)
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

    private fun setListAnswerText(data: ListQuestionAnswerResponseDto.QnaData) {
        with(binding) {
            tvAnswerOther.text = data.opponentAnswer
            tvAnswerMe.text = data.myAnswer
        }
        binding.clLoading.visibility = View.GONE
    }

    private fun setAnswerText(data: QuestionAnswerResponseDto.QnaData) {
        with(binding) {
            if (data.isOpponentAnswer == true) { //상대 답변 완료
                tvAnswerOther.text = data.opponentAnswer
                if (data.isMyAnswer == true) { // 내 답변 완료 (둘다 답변 한 상황)
                    tvAnswerMe.text = data.myAnswer
                    setOtherAnswerBlur(false)
                    isOtherHintVisible(false)
                    isMeHintVisible(false)
                } else { // 내 답변 x
                    setOtherAnswerBlur(true)
                    isOtherHintVisible(true)
                    isMeHintVisible(true)
                    isOnlyMeAnswered(false)
                    clAnswerMe.setBackgroundResource(R.drawable.shape_pri500_btn_stroke_r50_rect)
                }
            } else { // 상대 답변 x
                if (data.isMyAnswer == true) { // 내 답변 완료
                    tvAnswerMe.text = data.myAnswer
                    setOtherQuestionBlur(false)
                    isOnlyMeAnswered(true)
                    isMeHintVisible(false)
                    isOtherHintVisible(true)
                } else { // 내 답변 x (둘다 답변하지 않은 상황)
                    setOtherQuestionBlur(true)
                    isOnlyMeAnswered(false)
                    isMeHintVisible(true)
                    isOtherHintVisible(true)
                    clAnswerMe.setBackgroundResource(R.drawable.shape_pri500_btn_stroke_r50_rect)
                }
            }
        }
        binding.clLoading.visibility = View.GONE
    }

    private fun isOtherHintVisible(visibility : Boolean ){
        if (visibility){
            binding.tvOtherBlurHint.visibility = View.VISIBLE
        }
        else{
            binding.tvOtherBlurHint.visibility = View.INVISIBLE
        }
    }

    private fun isMeHintVisible(visibility : Boolean ){
        if (visibility){
            binding.tvMeBlurHint.visibility = View.VISIBLE
        }
        else{
            binding.tvMeBlurHint.visibility = View.INVISIBLE
        }
    }
    private fun isOnlyMeAnswered (isOnlyMeAnswered: Boolean) {
        with(binding) {
            if (isOnlyMeAnswered) {
                tvOtherBlurHint.text = getString(R.string.answer_only_me_hint)
            } else {
                tvOtherBlurHint.text = getString(R.string.answer_opponent_hint)
            }
        }
    }

    private fun setOtherQuestionBlur(isBlur: Boolean) {
        with(binding) {
            tvQuestionOther.setLayerType(View.LAYER_TYPE_SOFTWARE, null).apply {
                if (isBlur) tvQuestionOther.paint.maskFilter =
                    BlurMaskFilter(16f, BlurMaskFilter.Blur.NORMAL)
                else tvQuestionOther.paint.maskFilter = null
            }
        }
    }

    private fun setOtherAnswerBlur(isBlur: Boolean) {
        with(binding) {
            tvAnswerOther.setLayerType(View.LAYER_TYPE_SOFTWARE, null).apply {
                if (isBlur) tvAnswerOther.paint.maskFilter =
                    BlurMaskFilter(16f, BlurMaskFilter.Blur.NORMAL)
                else tvAnswerOther.paint.maskFilter = null
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

    private fun initLoadingGif() {
        val imageLoader = ImageLoader.Builder(applicationContext)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }.build()

        binding.ivLogoGif.load(R.raw.splash_logo, imageLoader = imageLoader)
    }

    override fun onResume() {
        super.onResume()
        observeQnaViewFlag()
    }
}