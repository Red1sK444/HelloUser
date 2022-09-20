package com.r3d1r4ph.hellouser.presentation.register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.r3d1r4ph.hellouser.R
import com.r3d1r4ph.hellouser.databinding.ActivityRegisterBinding
import com.r3d1r4ph.hellouser.presentation.common.exception.ExceptionHolder
import com.r3d1r4ph.hellouser.presentation.common.extensions.forEachView
import com.r3d1r4ph.hellouser.presentation.common.extensions.setErrorIfNotEmpty
import com.r3d1r4ph.hellouser.presentation.main.MainActivity
import com.r3d1r4ph.hellouser.presentation.register.model.RegisterAction
import com.r3d1r4ph.hellouser.presentation.register.model.RegisterInputFieldEnum
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class RegisterActivity : AppCompatActivity(R.layout.activity_register) {

    private val viewBinding by viewBinding(ActivityRegisterBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun setObservers() = with(viewModel) {
        uiState.observe(this@RegisterActivity) { uiState ->
            viewBinding.registerRegisterButton.isEnabled = uiState.isRegisterAvailable
            setInputFieldsErrors(uiState.inputFieldErrors)
        }
        uiAction.observe(this@RegisterActivity) { event ->
            event.getContentIfNotHandled()?.let { action ->
                when (action) {
                    is RegisterAction.OpenMainScreen -> openMainScreen()
                    is RegisterAction.Error -> {
                        Toast.makeText(
                            this@RegisterActivity,
                            getString(action.messageId),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is RegisterAction.ShowDatePickerDialog -> {
                        showDatePickerDialog { _, year, monthOfYear, dayOfMonth ->
                            viewBinding.registerBirthDateTextInputEditText
                                .setText("$dayOfMonth.$monthOfYear.$year")
                        }
                    }
                }
            }
        }
    }

    private fun setInputFieldsErrors(
        inputFieldErrors: Map<RegisterInputFieldEnum, ExceptionHolder?>
    ) = with(viewBinding) {
        registerTextInputLayoutGroup.forEachView<TextInputLayout> { view ->
            view.setErrorIfNotEmpty(
                inputFieldErrors[RegisterInputFieldEnum.fromTextInputLayoutId(
                    view.id
                )]
            )
        }
    }

    private fun openMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showDatePickerDialog(listener: DatePickerDialog.OnDateSetListener) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, listener, currentYear, currentMonth, currentDay).show()
    }

    private fun initView() {
        initInputFieldsErrorDismisses()

        viewBinding.registerRegisterButton.setOnClickListener {
            with(viewBinding) {
                viewModel.tryToRegister(
                    name = registerNameTextInputEditText.text.toString(),
                    surname = registerSurnameTextInputEditText.text.toString(),
                    birthDate = registerBirthDateTextInputEditText.text.toString(),
                    password = registerPasswordTextInputEditText.text.toString(),
                    confirmPassword = registerConfirmPasswordTextInputEditText.text.toString()
                )
            }
        }

        with(viewBinding.registerBirthDateTextInputEditText) {
            onFocusChangeListener =
                View.OnFocusChangeListener { view, hasFocus ->
                    if (hasFocus) {
                        view.clearFocus()
                        viewModel.onBirthDateClick()
                    }
                }
            isCursorVisible = false
        }

    }

    private fun initInputFieldsErrorDismisses() = with(viewBinding) {
        registerTextInputEditTextGroup.forEachView<TextInputEditText> { view ->
            view.doAfterTextChanged { editable ->
                viewModel.dismissIfNotBlankOrSetEmptyError(
                    editable,
                    RegisterInputFieldEnum.fromTextInputEditTextId(view.id)
                )
            }
        }
    }
}