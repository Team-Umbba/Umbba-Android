package com.ubcompany.umbba_android.presentation.onboarding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityInputInfoBinding
import com.ubcompany.umbba_android.domain.entity.User
import com.ubcompany.umbba_android.presentation.onboarding.quest.QuestActivity
import com.ubcompany.umbba_android.presentation.onboarding.viewmodel.InputInfoViewModel
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.hideKeyboard
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern
@AndroidEntryPoint
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
                        layoutInputName.error = getString(R.string.input_error)
                    } else if (etName.text.toString().length > 7) {
                        layoutInputName.error = getString(R.string.input_error_max_seven)
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
                        layoutInputYear.error = getString(R.string.input_error)
                    } else if (etYear.text.toString().length > 4) {
                        layoutInputYear.error = getString(R.string.input_error_limit_four)
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
                        .isNotEmpty() && etName.text.toString()
                        .isNotEmpty() && (chip1.isChecked || chip2.isChecked)
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
        binding.btnNext.setOnSingleClickListener {
            val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("userData", User::class.java)
            } else {
                intent.getParcelableExtra<User>("userData")
            }
            if (userData != null) {
                saveInfo(userData)
                if (!userData.isReceiver) {
                    startActivity(Intent(this, SelectFamilyActivity::class.java).apply {
                        putExtra("userData", userData)
                    })
                } else {
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        this.currentFocus?.let { hideKeyboard(it) }
        with(binding) {
            etName.clearFocus()
            etYear.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}