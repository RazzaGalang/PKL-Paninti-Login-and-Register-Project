package com.razzagalangadzan.pklpaninti.LoginAndRegister

import android.annotation.SuppressLint
import android.content.Intent
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
import com.razzagalangadzan.pklpaninti.MainActivity
import com.razzagalangadzan.pklpaninti.R
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
    val onlyCharacterRegex = "[A-Za-z '-]+"

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

    private fun view() {
        spannable()
        fullnameTextWatcher()
        usernameTextWatcher()
        emailAddressTextWatcher()
        passwordTextWatcher()
        confirmPasswordTextWatcher()
        signUpClicked()
    }

    @SuppressLint("ResourceAsColor")
    private fun spannable() {
        val spannable = SpannableStringBuilder(binding.tvTextLogin.text.toString())
        val blueColor = ForegroundColorSpan(R.color.primary500)
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
        binding.tvTextLogin.text = spannable
        binding.tvTextLogin.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun fullnameTextWatcher() {
        binding.tvFullName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    nullFullName()
                } else if (!(s.toString().matches(minNameRegex.toRegex()))) {
                    regexMinFullname()
                } else if (!(s.toString().matches(onlyCharacterRegex.toRegex()))) {
                    regexOnlyCharacter()
                } else {
                    clearFullName()
                }
            }

//            TODO : Regex buat Character onlynya belum selesai

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

    private fun validationAllData() {
        nullCheck()
        validationTrue()
    }

    private fun validationTrue() {
        if (isNullFullName() && isNullUsername() && isNullEmailAddress() && isNullPassword() && isNullConfirmPassword()) binding()
    }

    private fun nullCheck() {
        isNullFullName()
        isNullUsername()
        isNullEmailAddress()
        isNullPassword()
        isNullConfirmPassword()
    }

    private fun isNullFullName(): Boolean {
        isNullFullName = if (binding.tvFullName.length() == 0) {
            nullFullName()
            false
        } else {
            true
        }

        return isNullFullName
    }

    private fun isNullUsername(): Boolean {
        isNullUsername = if (binding.tvUsername.length() == 0) {
            nullUsername()
            false
        } else {
            true
        }

        return isNullUsername
    }

    private fun isNullEmailAddress(): Boolean {
        isNullEmailAddress = if (binding.tvEmailAddress.length() == 0) {
            nullEmailAddress()
            false
        } else {
            true
        }

        return isNullEmailAddress
    }

    private fun isNullPassword(): Boolean {
        isNullPassword = if (binding.tvPassword.length() == 0) {
            nullPassword()
            false
        } else {
            true
        }

        return isNullPassword
    }

    private fun isNullConfirmPassword(): Boolean {
        isNullConfirmPassword = if (binding.tvConfirmPassword.length() == 0) {
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
        binding.txtFullName.error = getText(R.string.error_text_null_fullname)
        errorBorderFullName()
        return false
    }

    private fun nullUsername(): Boolean {
        binding.txtUsername.error = getText(R.string.error_text_null_username)
        errorBorderUsername()
        return false
    }

    private fun nullEmailAddress(): Boolean {
        binding.txtEmailAddress.error = getText(R.string.error_text_null_email_address)
        errorBorderEmail()
        return false
    }

    private fun nullPassword(): Boolean {
        binding.txtPassword.error = getText(R.string.error_text_null_password)
        errorBorderPassword()
        return false
    }

    private fun nullConfirmPassword(): Boolean {
        binding.txtConfirmPassword.error = getText(R.string.error_text_null_confirm_password)
        errorBorderConfirmPassword()
        return false
    }

    private fun regexOnlyCharacter(): Boolean {
        binding.txtFullName.error = getText(R.string.error_text_regex_only_character)
        errorBorderFullName()
        return false
    }

    private fun regexMinFullname(): Boolean {
        binding.txtFullName.error = getText(R.string.error_text_regex_min_fullname)
        errorBorderFullName()
        return false
    }

    private fun regexMinUsername(): Boolean {
        binding.txtUsername.error = getText(R.string.error_text_regex_min_username)
        errorBorderUsername()
        return false
    }

    private fun regexEmailAddress(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target.toString()).matches()
    }

    private fun regexEmailAddressResult(): Boolean {
        binding.txtEmailAddress.error = getText(R.string.error_text_regex_format_email)
        errorBorderEmail()
        return false
    }

    private fun regexPassword(): Boolean {
        binding.txtPassword.error = getText(R.string.error_text_regex_password)
        errorBorderPassword()
        return false
    }

    private fun regexSymbolUsername(): Boolean {
        binding.txtUsername.error = getText(R.string.error_text_regex_symbol_username)
        errorBorderUsername()
        return false
    }

    private fun validatePassword(): Boolean {
        binding.txtConfirmPassword.error = getText(R.string.error_text_validate_password)
        errorBorderConfirmPassword()
        return false
    }

    private fun clearFullName(): Boolean {
        binding.txtFullName.isErrorEnabled = false
        defaultBorderFullName()
        return true
    }

    private fun clearUsername(): Boolean {
        binding.txtUsername.isErrorEnabled = false
        defaultBorderUsername()
        return true
    }

    private fun clearEmailAddress(): Boolean {
        binding.txtEmailAddress.isErrorEnabled = false
        defaultBorderEmail()
        return true
    }

    private fun clearPassword(): Boolean {
        binding.txtPassword.isErrorEnabled = false
        defaultBorderPassword()
        return true
    }

    private fun clearConfirmPassword(): Boolean {
        binding.txtConfirmPassword.isErrorEnabled = false
        defaultBorderConfirmPassword()
        return true
    }

    private fun defaultBorderFullName() {
        binding.tvFullName.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun defaultBorderUsername() {
        binding.tvUsername.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun defaultBorderEmail(){
        binding.tvEmailAddress.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun defaultBorderPassword() {
        binding.tvPassword.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun defaultBorderConfirmPassword() {
        binding.tvConfirmPassword.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun errorBorderFullName(){
        binding.tvFullName.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun errorBorderUsername() {
        binding.tvUsername.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun errorBorderEmail() {
        binding.tvEmailAddress.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun errorBorderPassword() {
        binding.tvPassword.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun errorBorderConfirmPassword(){
        binding.tvConfirmPassword.setBackgroundResource(R.drawable.bg_white_red_outline)
    }
}