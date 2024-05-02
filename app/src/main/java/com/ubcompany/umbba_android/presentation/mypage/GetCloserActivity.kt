package com.ubcompany.umbba_android.presentation.mypage

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityGetCloseBinding
import com.ubcompany.umbba_android.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetCloserActivity : BindingActivity<ActivityGetCloseBinding>(R.layout.activity_get_close),
    View.OnClickListener, CloserQuestionCallback {

    private val viewModel by viewModels<GetCloserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewmodel = viewModel
        viewModel.closerActivityCallback = this
        initCloserFragment()
        changeFragmentObserve()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_back -> {
                finish()
            }
        }
    }

    override fun onQuestionRetrieved() {
        initCloserFragment()
    }


    private fun changeFragmentObserve() {
        viewModel.responseStatus.observe(this@GetCloserActivity) {
            if (it == SUCCESS_GET_CLOSER_RESPONSE) {
                if (viewModel.changeQuestionFragment.value == true) {
                    changeFragment(GetCloserQuestionFragment(viewModel))
                }
                if (viewModel.changeResultFragment.value == true) {
                    changeFragment(GetCloserResultFragment(viewModel))
                }
            }
        }
    }

    private fun initFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_closer_fragment, fragment)
            .commit()
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_closer_fragment, fragment)
            .commit()
        initAnswerChip()
    }

    private fun initCloserFragment() {
        val userAnswerCase = viewModel.closerQuestionResponse.value?.responseCase
        if (userAnswerCase != null) {
            if (userAnswerCase == ME_NO_ANSWER) {
                initFragment(GetCloserQuestionFragment(viewModel))
            } else {
                initFragment(GetCloserResultFragment(viewModel))
            }
        }
    }

    private fun initAnswerChip() {
        viewModel.isCheckedAnswerOne.value = false
        viewModel.isCheckedAnswerTwo.value = false
    }

    companion object {
        const val ME_NO_ANSWER = 1
        const val SUCCESS_GET_CLOSER_RESPONSE = 200
    }
}

interface CloserQuestionCallback {
    fun onQuestionRetrieved()

}