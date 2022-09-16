package com.r3d1r4ph.hellouser.presentation.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.r3d1r4ph.hellouser.R
import com.r3d1r4ph.hellouser.databinding.ActivityRegisterBinding
import com.r3d1r4ph.hellouser.presentation.common.extensions.setErrorIfNotEmpty
import com.r3d1r4ph.hellouser.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity(R.layout.activity_register) {

    private val viewBinding by viewBinding(ActivityRegisterBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        initView()
    }

    private fun setObservers() = with(viewModel) {
        uiState.observe(this@RegisterActivity) { uiState ->
            viewBinding.registerRegisterButton.isEnabled = uiState.isRegisterAvailable
            setInputFieldsErrors(uiState.inputFieldErrors)
        }
        uiAction.observe(this@RegisterActivity) { event ->
            event.getContentIfNotHandled()?.let { action ->
                when (action) {
                    is RegisterAction.OpenMainScreen -> openMainScreen()
                }
            }
        }
    }

    private fun setInputFieldsErrors(
        inputFieldErrors: Map<RegisterInputFieldEnum, Int?>
    ) = with(viewBinding) {
        registerTextInputLayoutGroup.referencedIds
            .map { id -> findViewById(id) as? TextInputLayout }
            .filterNotNull()
            .forEach { view ->
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

    private fun initView() {
        initInputFieldsErrorDismisses()

        viewBinding.registerRegisterButton.setOnClickListener {
            with(viewBinding) {

            }
        }
    }

    private fun initInputFieldsErrorDismisses() = with(viewBinding) {
        registerTextInputEditTextGroup.referencedIds
            .map { id -> findViewById(id) as? TextInputEditText }
            .filterNotNull()
            .forEach { view ->
                view.addTextChangedListener { editable ->
                    viewModel.dismissIfNotBlankOrSetEmptyError(
                        editable,
                        RegisterInputFieldEnum.fromTextInputEditTextId(view.id)
                    )
                }
            }
    }
}