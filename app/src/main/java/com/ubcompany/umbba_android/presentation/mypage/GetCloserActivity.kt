package com.ubcompany.umbba_android.presentation.mypage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityGetCloseBinding
import com.ubcompany.umbba_android.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetCloserActivity : BindingActivity<ActivityGetCloseBinding>(R.layout.activity_get_close), View.OnClickListener {

    private val viewModel by viewModels<GetCloserViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewmodel = viewModel
        changeFragmentObserve()
        initFragmentView()
    }

    override fun onClick(view : View) {
        when (view.id) {
            R.id.iv_back -> {
                finish()
            }
        }
    }

    private fun changeFragmentObserve(){
        viewModel.changeResultFragment.observe(this@GetCloserActivity){
            if (it) changeFragment(GetCloserResultFragment(viewModel))
        }

        viewModel.changeQuestionFragment.observe(this@GetCloserActivity) {
            if (it) changeFragment(GetCloserQuestionFragment(viewModel))
        }
    }
    private fun initFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_closer_question,fragment)
            .commit()
    }

    private fun changeFragment(fragment :Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_closer_question, fragment)
            .commit()
        initAnswerChip()
    }

    private fun initAnswerChip(){
        viewModel.isCheckedAnswerOne.value = false
        viewModel.isCheckedAnswerTwo.value = false
    }

    private fun initFragmentView(){
        viewModel.closerQuestionResponse.observe(this@GetCloserActivity){
            Log.e("hyeon","closer response 들어옴 ${it}")
            if (it.responseCase == ME_NO_ANSWER) {
                initFragment(GetCloserQuestionFragment(viewModel))
            }
            else initFragment(GetCloserResultFragment(viewModel))
        }
    }

    companion object{
        const val ME_NO_ANSWER = 1
        const val OPPONENT_NO_ANSWER = 2
        const val BOTH_ANSWER_SAME = 3
        const val BOTH_ANSWER_DIFFERENT = 4
    }
}