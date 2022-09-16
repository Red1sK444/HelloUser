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
import com.r3d1r4ph.hellouser.presentation.common.extensions.setError
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
            setErrors(uiState.inputFieldErrors)
        }
        uiAction.observe(this@RegisterActivity) { event ->
            event.getContentIfNotHandled()?.let { action ->
                when (action) {
                    is RegisterAction.OpenMainScreen -> openMainScreen()
                }
            }
        }
    }

    private fun openMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun initView() {
        setErrorDismisses()

        viewBinding.registerRegisterButton.setOnClickListener {
            with(viewBinding) {

            }
        }
    }

    private fun setErrorDismisses() = with(viewBinding) {
        registerTextInputEditTextGroup.referencedIds
            .map { id -> findViewById(id) as? TextInputEditText }
            .filterNotNull()
            .forEach { view ->
                view.addTextChangedListener {
                    viewModel.dismissError(RegisterInputFieldEnum.fromTextInputEditTextId(view.id))
                }
            }
    }

    private fun setErrors(
        inputFieldErrors: Map<RegisterInputFieldEnum, Int?>
    ) = with(viewBinding) {
        registerTextInputLayoutGroup.referencedIds
            .map { id -> findViewById(id) as? TextInputLayout }
            .filterNotNull()
            .forEach { view ->
                view.setError(inputFieldErrors[RegisterInputFieldEnum.fromTextInputLayoutId(view.id)])
            }
    }
}