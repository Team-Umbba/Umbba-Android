package com.ubcompany.umbba_android.presentation.qna

import android.content.Intent
import android.graphics.BlurMaskFilter
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.awaitBalloons
import com.skydoves.balloon.overlay.BalloonOverlayRoundRect
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.data.model.response.ListQuestionAnswerResponseDto
import com.ubcompany.umbba_android.data.model.response.QuestionAnswerResponseDto
import com.ubcompany.umbba_android.databinding.ActivityQuestionAnswerBinding
import com.ubcompany.umbba_android.presentation.qna.viewmodel.QuestionAnswerViewModel
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionAnswerActivity :
    BindingActivity<ActivityQuestionAnswerBinding>(R.layout.activity_question_answer),
    View.OnClickListener {
    private val viewModel by viewModels<QuestionAnswerViewModel>()
    private var isShowedBalloon = false
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

    private fun setUserAnswerClickEvent(data: QuestionAnswerResponseDto.QnaData) {
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
            setAnswerBtnEnable(true)
            setListAnswerText(it)
        }
    }

    private fun observeQnaResponse() {
        viewModel.qnaResponse.observe(this@QuestionAnswerActivity) {
            setAnswerText(it)
            setUserAnswerClickEvent(it)
            setAnswerBtnEnable(it.isMyAnswer)
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
                    setOtherQuestionTextColor(tvQuestionOther)
                    setOtherQuestionTextColor(tvAnswerOther)
                    clAnswerMe.setBackgroundResource(R.drawable.shape_grey300_stroke_r17_rect)
                } else { // 내 답변 x
                    setOtherAnswerBlur(true)
                    isOtherHintVisible(true)
                    isMeHintVisible(true)
                    isOnlyMeAnswered(false)
                    setOtherQuestionTextColor(tvQuestionOther)
                }
            } else { // 상대 답변 x
                if (data.isMyAnswer == true) { // 내 답변 완료
                    tvAnswerMe.text = data.myAnswer
                    setOtherQuestionBlur(false)
                    isOnlyMeAnswered(true)
                    isMeHintVisible(false)
                    isOtherHintVisible(true)
                    setOtherQuestionTextColor(tvQuestionOther)
                    clAnswerMe.setBackgroundResource(R.drawable.shape_grey300_stroke_r17_rect)
                } else { // 내 답변 x (둘다 답변하지 않은 상황)
                    setOtherQuestionBlur(true)
                    isOnlyMeAnswered(false)
                    isMeHintVisible(true)
                    isOtherHintVisible(true)
                }
            }
        }
        binding.clLoading.visibility = View.GONE
        if (!isShowedBalloon) {
            isSetUpTutorialView()
            isShowedBalloon = true
        }
    }

    private fun setOtherQuestionTextColor(textView: TextView) {
        textView.setTextColor(
            ContextCompat.getColor(
                this@QuestionAnswerActivity,
                R.color.black_500
            )
        )
    }

    private fun isOtherHintVisible(visibility: Boolean) {
        if (visibility) {
            binding.tvOtherBlurHint.visibility = View.VISIBLE
        } else {
            binding.tvOtherBlurHint.visibility = View.INVISIBLE
        }
    }

    private fun isMeHintVisible(visibility: Boolean) {
        if (visibility) {
            binding.tvMeBlurHint.visibility = View.VISIBLE
        } else {
            binding.tvMeBlurHint.visibility = View.INVISIBLE
        }
    }

    private fun isOnlyMeAnswered(isOnlyMeAnswered: Boolean) {
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

    private fun setAnswerBtnEnable(enable: Boolean?) {
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

    private fun isSetUpTutorialView() {
        showTutorialView()
    }

    private fun showTutorialView() {
        val btnAnswerBalloon = createBalloon("답변은 상대가 확인할 수 있으니\n잘 답변해줘",80f,0.5f)
        val tvQuestionBalloon = createBalloon("답변을 입력하면\n상대가 받은 질문을 알 수 있어", 50f,0.2f)
        lifecycleScope.launch {
            awaitBalloons {
                dismissSequentially = false
                btnAnswerBalloon.showAlignTop(binding.btnAnswer)
                tvQuestionBalloon.showAlignBottom(binding.tvQuestionOther)
            }
        }
    }
    private fun createBalloon(text: String, radius: Float, arrowPosition: Float): Balloon {
        return Balloon.Builder(this@QuestionAnswerActivity)
            .setWidth(BalloonSizeSpec.WRAP)
            .setHeight(BalloonSizeSpec.WRAP)
            .setTextColorResource(R.color.grey_900)
            .setBackgroundColorResource(R.color.white_500)
            .setTextGravity(Gravity.CENTER)
            .setText(text)
            .setTextSize(16f)
            .setPaddingHorizontal(16)
            .setPaddingVertical(6)
            .setCornerRadius(4f)
            .setIsVisibleArrow(true)
            .setArrowSize(12)
            .setArrowPosition(arrowPosition)
            .setIsVisibleOverlay(true)
            .setLifecycleOwner(this@QuestionAnswerActivity)
            .setArrowOrientation(ArrowOrientation.BOTTOM)
            .setOverlayColorResource(R.color.black_opacity30)
            .setOverlayShape(BalloonOverlayRoundRect(radius, radius))
            .setMarginBottom(8)
            .setMarginTop(8)
            .setDismissWhenOverlayClicked(true)
            .build()
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