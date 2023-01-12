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

    private var _binding: FragmentLoginBinding? = null;
    private val binding get() = _binding!!;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false);
        val view = binding.root;

        //TODO : Spannable Area and Different Color Text
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

        binding.tfEmailAtauUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.tfEmailAtauUsername.length() == 0) {
                    binding.emailAtauUsername.error = "Email atau Username wajib diisi"
                } else {
                    binding.emailAtauUsername.isErrorEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.tfPasswordLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.tfPasswordLogin.length() == 0) {
                    binding.passwordLogin.error = "Password wajib diisi"
                    binding.passwordLogin.errorIconDrawable = null
                } else {
                    binding.passwordLogin.isErrorEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.buttonMasuk.setOnClickListener {
            if (binding.tfEmailAtauUsername.length() == 0 && binding.tfPasswordLogin.length() == 0) {
                binding.emailAtauUsername.error = "Email atau Username wajib diisi"
                binding.passwordLogin.error = "Password wajib diisi"
            } else if (binding.tfEmailAtauUsername.length() == 0) {
                binding.emailAtauUsername.error = "Email atau Username wajib diisi"
            } else if (binding.tfPasswordLogin.length() == 0) {
                binding.passwordLogin.error = "Password wajib diisi"
            } else {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
        }

        return view;

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}