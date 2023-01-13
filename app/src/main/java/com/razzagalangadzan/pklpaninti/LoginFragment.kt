package com.razzagalangadzan.pklpaninti

import android.content.Intent
import android.graphics.Color
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

        view()

        return view

    }

    private fun view() {
        spannable()
        emailOrUsername()
        password()
        login()
    }

    private fun emailOrUsername() {
        binding.tfEmailOrUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    nullEmailOrUsername()
                } else {
                    correctEmailOrUsername()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun password() {
        binding.tfLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    nullPassword()
                } else {
                    correctPassword()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun login() {
        binding.buttonMasuk.setOnClickListener {
            validateNullUsernamePassword()
            validateNullPassword()

            if (validateNullUsernamePassword() && validateNullPassword()){
                binding()
            }
        }
    }

    private fun validateNullUsernamePassword(): Boolean {
        if (binding.tfEmailOrUsername.length() == 0){
            nullEmailOrUsername()
            validUser = false
        } else {
            validUser = true
        }

        return validUser
    }

    private fun validateNullPassword(): Boolean {
        if (binding.tfLoginPassword.length() == 0){
            nullPassword()
            validPass = false
        } else {
            validPass = true
        }

        return validPass
    }

    private fun nullEmailOrUsername(): Boolean {
        binding.emailOrUsername.error = "Email atau Username wajib diisi"
        return false
    }

    private fun nullPassword(): Boolean {
        binding.loginPassword.error = "Password wajib diisi"
        return false
    }

    private fun correctEmailOrUsername(): Boolean {
        binding.emailOrUsername.isErrorEnabled = false
        return true
    }

    private fun correctPassword(): Boolean {
        binding.loginPassword.isErrorEnabled = false
        return true
    }

    private fun spannable() {
        val spannable = SpannableStringBuilder(binding.textRegister.text.toString())
        val blueColor = ForegroundColorSpan(Color.parseColor("#55BCE0"))
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
        binding.textRegister.text = spannable
        binding.textRegister.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun binding(){
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}