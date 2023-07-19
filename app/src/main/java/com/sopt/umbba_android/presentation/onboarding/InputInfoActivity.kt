package com.sopt.umbba_android.presentation.onboarding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityInputInfoBinding
import com.sopt.umbba_android.domain.entity.User
import com.sopt.umbba_android.presentation.onboarding.quest.QuestActivity
import com.sopt.umbba_android.presentation.onboarding.viewmodel.InputInfoViewModel
import com.sopt.umbba_android.util.binding.BindingActivity
import java.util.regex.Pattern

class InputInfoActivity : BindingActivity<ActivityInputInfoBinding>(R.layout.activity_input_info),
    View.OnClickListener {

    private val viewModel by viewModels<InputInfoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        binding.viewmodel = viewModel

        checkAllInfoComplete()
        validateName()
        validateYear()
        goSelectFamilyActivity()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> finish()
        }
    }

    private fun validateName() {
        with(binding) {
            etName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val namePattern = Pattern.compile(HANGUL_PATTERN)
                    if (!etName.text?.matches(namePattern.toRegex())!!) {
                        layoutInputName.error = "*형식이 올바르지 않습니다."
                    } else if (etName.text.toString().length > 7) {
                        layoutInputName.error = "*7글자 이하로 작성해주세요."
                    } else {
                        layoutInputName.error = null
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun validateYear() {
        with(binding) {
            etYear.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val yearPattern = Pattern.compile(NUMBER_PATTERN)
                    if (!etYear.text?.matches(yearPattern.toRegex())!!) {
                        layoutInputYear.error = "*형식이 올바르지 않습니다."
                    } else if (etYear.text.toString().length > 4) {
                        layoutInputYear.error = "*4글자로 작성해주세요."
                    } else {
                        layoutInputYear.error = null
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun checkAllInfoComplete() {
        viewModel.name.observe(this) {
            viewModel.checkInfoComplete()
        }
        viewModel.man.observe(this) {
            viewModel.checkInfoComplete()
        }
        viewModel.woman.observe(this) {
            viewModel.checkInfoComplete()
        }
        viewModel.year.observe(this) {
            viewModel.checkInfoComplete()
        }
        viewModel.isAllInfoComplete.observe(this) {
            with(binding) {
                btnNext.isEnabled =
                    layoutInputName.error.isNullOrEmpty() && layoutInputYear.error.isNullOrEmpty() && etYear.text.toString()
                        .isNotEmpty() && etName.text.toString().isNotEmpty()
            }
        }
    }

    private fun saveInfo(user: User) {
        with(binding) {
            user.name = etName.text.toString()
            user.gender =
                if (chip1.isChecked) {
                    chip1.text.toString()
                } else if (chip2.isChecked) {
                    chip2.text.toString()
                } else {
                    null
                }
            user.bornYear = etYear.text.toString().toInt()
        }
    }

    private fun goSelectFamilyActivity() {
        binding.btnNext.setOnClickListener {
            val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("userData", User::class.java)
            } else {
                intent.getParcelableExtra<User>("userData")
            }
            if (userData != null) {
                saveInfo(userData)
                if (!userData.isReceiver) { //초대하는 사람
                    Log.e(
                        "yeonjin",
                        "inputInfo parcelable : ${userData?.isReceiver} + ${userData?.name} + ${userData?.gender} + ${userData?.bornYear}"
                    )
                    startActivity(Intent(this, SelectFamilyActivity::class.java).apply {
                        putExtra("userData", userData)
                    })
                } else { //초대받는 사람
                    Log.e(
                        "yeonjin",
                        "inputInfo parcelable : ${userData?.isReceiver} + ${userData?.name} + ${userData?.gender} + ${userData?.bornYear}"
                    )
                    startActivity(Intent(this, QuestActivity::class.java).apply {
                        putExtra("userData", userData)
                    })
                }
            }
        }
    }

    companion object {
        const val HANGUL_PATTERN = "^[가-힣]*$"
        const val NUMBER_PATTERN = "^([12]\\d{3})*$"
    }
}