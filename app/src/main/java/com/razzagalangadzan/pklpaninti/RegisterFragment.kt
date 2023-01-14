package com.razzagalangadzan.pklpaninti

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.razzagalangadzan.pklpaninti.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private var isNullFullName = false
    private var isNullUsername = false
    private var isNullEmailAddress = false
    private var isNullPassword = false
    private var isNullConfirmPassword = false

    val minNameRegex = "^.{2,}$"
    val minUserRegex = "^.{6,}$"
    val passCharacterRegex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$"
    val userSymbolRegex = "[a-zA-Z0-9._]+"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun view(){
        spannable()
        fullnameTextWatcher()
        usernameTextWatcher()
        emailAddressTextWatcher()
        passwordTextWatcher()
        confirmPasswordTextWatcher()
        signUpClicked()
    }

    private fun spannable() {
        val spannable = SpannableStringBuilder(binding.textLogin.text.toString())
        val blueColor = ForegroundColorSpan(Color.parseColor("#55BCE0"))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val fragment = LoginFragment()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.flFragment, fragment)?.commit()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }

        spannable.setSpan(blueColor, 18, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(clickableSpan, 18, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textLogin.text = spannable
        binding.textLogin.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun fullnameTextWatcher() {
        binding.tvFullName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    nullFullName()
                } else if (s.toString().matches(minNameRegex.toRegex())) {
                    clearFullName()
                } else {
                    regexMinFullname()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun usernameTextWatcher() {
        binding.tvUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    nullUsername()
                } else if (s.toString().matches(minUserRegex.toRegex()) && s.toString()
                        .matches(userSymbolRegex.toRegex())
                ) {
                    clearUsername()
                } else if (!(s.toString().matches(userSymbolRegex.toRegex()))) {
                    regexSymbolUsername()
                } else {
                    regexMinUsername()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun emailAddressTextWatcher() {
        binding.tvEmailAddress.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    nullEmailAddress()
                } else if (!regexEmailAddress(binding.tvEmailAddress.text.toString())) {
                    regexEmailAddressResult()
                } else {
                    clearEmailAddress()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun passwordTextWatcher() {
        binding.tvPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    nullPassword()
                } else if (!(s.toString().matches(passCharacterRegex.toRegex()))) {
                    regexPassword()
                    validatePassword()
                } else if (binding.tvPassword.text.toString() != binding.tvConfirmPassword.text.toString()) {
                    validatePassword()
                    clearPassword()
                } else if (binding.tvPassword.text.toString() == binding.tvConfirmPassword.text.toString()) {
                    clearPassword()
                    clearConfirmPassword()
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

    private fun confirmPasswordTextWatcher() {
        binding.tvConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    nullConfirmPassword()
                } else if (binding.tvPassword.text.toString() != binding.tvConfirmPassword.text.toString()) {
                    validatePassword()
                    clearPassword()
                } else if (binding.tvPassword.text.toString() == binding.tvConfirmPassword.text.toString()) {
                    clearConfirmPassword()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun signUpClicked() {
        binding.btnSignUp.setOnClickListener {
            validationAllData()
        }
    }

    private fun validationAllData(){
        nullCheck()
        validationTrue()
    }

    private fun validationTrue(){
        if (isNullFullName() && isNullUsername() && isNullEmailAddress() && isNullPassword() && isNullConfirmPassword()) binding()
    }

    private fun nullCheck(){
        isNullFullName()
        isNullUsername()
        isNullEmailAddress()
        isNullPassword()
        isNullConfirmPassword()
    }

    private fun isNullFullName(): Boolean {
        isNullFullName = if (binding.tvFullName.length() == 0){
            nullFullName()
            false
        } else {
            true
        }

        return isNullFullName
    }

    private fun isNullUsername(): Boolean {
        isNullUsername = if (binding.tvUsername.length() == 0){
            nullUsername()
            false
        } else {
            true
        }

        return isNullUsername
    }

    private fun isNullEmailAddress(): Boolean {
        isNullEmailAddress = if (binding.tvEmailAddress.length() == 0){
            nullEmailAddress()
            false
        } else {
            true
        }

        return isNullEmailAddress
    }

    private fun isNullPassword(): Boolean {
        isNullPassword = if (binding.tvPassword.length() == 0){
            nullPassword()
            false
        } else {
            true
        }

        return isNullPassword
    }

    private fun isNullConfirmPassword(): Boolean {
        isNullConfirmPassword = if (binding.tvConfirmPassword.length() == 0){
            nullConfirmPassword()
            false
        } else {
            true
        }

        return isNullConfirmPassword
    }

    private fun binding() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun nullFullName(): Boolean {
        binding.txtFullName.error = "Nama lengkap wajib diisi!"
        return false
    }

    private fun nullUsername(): Boolean {
        binding.txtUsername.error = "Username wajib diisi!"
        return false
    }

    private fun nullEmailAddress(): Boolean {
        binding.txtEmailAddress.error = "Email wajib diisi!"
        return false
    }

    private fun nullPassword(): Boolean {
        binding.txtPassword.error = "Password wajib diisi!"
        return false
    }

    private fun nullConfirmPassword(): Boolean {
        binding.txtConfirmPassword.error = "Konfirmasi Password wajib diisi!"
        return false
    }

    private fun regexMinFullname(): Boolean {
        binding.txtFullName.error = "Nama lengkap minimal 2 karakter"
        return false
    }

    private fun regexMinUsername(): Boolean {
        binding.txtUsername.error = "Username minimal 6 karakter"
        return false
    }

    private fun regexEmailAddress(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun regexEmailAddressResult(): Boolean {
        binding.txtEmailAddress.error = "Format email tidak sesuai"
        return false
    }

    private fun regexPassword(): Boolean {
        binding.txtPassword.error =
            "Password minimal berisi 6 karakter, 1 huruf kapital dan 1 angka"
        return false
    }

    private fun regexSymbolUsername(): Boolean {
        binding.txtUsername.error = "Username tidak bisa menggunakan simbol selain . dan _"
        return false
    }

    private fun validatePassword(): Boolean {
        binding.txtConfirmPassword.error = "Konfirmasi password tidak sesuai!"
        return false
    }

    private fun clearFullName(): Boolean {
        binding.txtFullName.isErrorEnabled = false
        return true
    }

    private fun clearUsername(): Boolean {
        binding.txtUsername.isErrorEnabled = false
        return true
    }

    private fun clearEmailAddress(): Boolean {
        binding.txtEmailAddress.isErrorEnabled = false
        return true
    }

    private fun clearPassword(): Boolean {
        binding.txtPassword.isErrorEnabled = false
        return true
    }

    private fun clearConfirmPassword(): Boolean {
        binding.txtConfirmPassword.isErrorEnabled = false
        return true
    }
}