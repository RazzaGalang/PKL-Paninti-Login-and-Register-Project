package com.razzagalangadzan.pklpaninti

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.razzagalangadzan.pklpaninti.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var validUser = false
    private var validPass = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view()
    }

    private fun view() {
        spannable()
        emailOrUsername()
        password()
        login()
    }

    private fun emailOrUsername() {
        binding.tvEmailOrUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    nullEmailOrUsername()
                } else {
                    clearEmailOrUsername()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun password() {
        binding.tvPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    nullPassword()
                } else {
                    clearPassword()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            validateNullUsernamePassword()
            validateNullPassword()

            if (validateNullUsernamePassword() && validateNullPassword()) binding()
        }
    }

    private fun validateNullUsernamePassword(): Boolean {
        validUser = if (binding.tvEmailOrUsername.length() == 0) {
            nullEmailOrUsername()
            false
        } else {
            true
        }

        return validUser
    }

    private fun validateNullPassword(): Boolean {
        validPass = if (binding.tvPassword.length() == 0) {
            nullPassword()
            false
        } else {
            true
        }

        return validPass
    }

    private fun nullEmailOrUsername(): Boolean {
        binding.txtEmailOrUsername.error = R.string.error_text_null_email_and_username.toString()
        errorBorderEmailOrUsername()
        return false
    }

    private fun nullPassword(): Boolean {
        binding.txtPaswword.error = R.string.error_text_null_password.toString()
        errorBorderPassword()
        return false
    }

    private fun clearEmailOrUsername(): Boolean {
        binding.txtEmailOrUsername.isErrorEnabled = false
        defaultBorderEmailOrUsername()
        return true
    }

    private fun clearPassword(): Boolean {
        binding.txtPaswword.isErrorEnabled = false
        defaultBorderPassword()
        return true
    }

    private fun defaultBorderEmailOrUsername() {
        binding.tvEmailOrUsername.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun defaultBorderPassword() {
        binding.tvPassword.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun errorBorderEmailOrUsername() {
        binding.tvEmailOrUsername.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun errorBorderPassword() {
        binding.tvPassword.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    @SuppressLint("ResourceAsColor")
    private fun spannable() {
        val spannable = SpannableStringBuilder(binding.tvTextRegister.text.toString())
        val blueColor = ForegroundColorSpan(R.color.primary500)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val fragment = RegisterFragment()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.flFragment, fragment)?.commit()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }

        spannable.setSpan(blueColor, 18, 31, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(clickableSpan, 18, 31, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvTextRegister.text = spannable
        binding.tvTextRegister.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun binding() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}