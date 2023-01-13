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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.razzagalangadzan.pklpaninti.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    var validationNamaLengkap = false
    var validationUsername = false
    var validationEmail = false
    var validationPassword = false
    var validationKonfirmPaswword = false
    private var validationJenisKelamin = false

    val minNameRegex = "^.{2,}$"
    val minUserRegex = "^.{6,}$"
    val validPassRegex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$"
    val validUser = "[a-zA-Z0-9._]+"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        //TODO : Spannable Area and Different Color Text
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

        binding.tfNamaLengkap.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    binding.namaLengkap.error = "Nama lengkap wajib diisi!"
                } else if (s.toString().matches(minNameRegex.toRegex())) {
                    binding.namaLengkap.isErrorEnabled = false
                    validationNamaLengkap = true
                } else {
                    binding.namaLengkap.error = "Nama lengkap minimal 2 karakter"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.tfUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    binding.username.error = "Username wajib diisi!"
                } else if (s.toString().matches(minUserRegex.toRegex()) && s.toString()
                        .matches(validUser.toRegex())
                ) {
                    binding.username.isErrorEnabled = false
                    validationUsername = true
                } else if (!(s.toString().matches(validUser.toRegex()))) {
                    binding.username.error = "Username tidak bisa menggunakan simbol selain . dan _"
                    validationUsername = false
                } else {
                    binding.username.error = "Username minimal 6 karakter"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.tfAlamatEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    binding.alamatEmail.error = "Email wajib diisi!"
                } else if (!isValidEmail(binding.tfAlamatEmail.text.toString())) {
                    binding.alamatEmail.error = "Format email tidak sesuai"
                } else {
                    binding.alamatEmail.isErrorEnabled = false
                    validationEmail = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.tfPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    binding.password.error = "Password wajib diisi!"
                } else if (!(s.toString().matches(validPassRegex.toRegex()))) {
                    binding.password.error =
                        "Password minimal berisi 6 karakter, 1 huruf kapital dan 1 angka"
                    binding.konfirmPassword.error = "Konfirmasi password tidak sesuai!"
                    validationPassword = false
                    validationKonfirmPaswword = false
                } else if (binding.tfPassword.text.toString() != binding.tfKonfirmasiPassoword.text.toString()) {
                    binding.konfirmPassword.error = "Konfirmasi password tidak sesuai!"
                    binding.password.isErrorEnabled = false
                    validationPassword = true
                    validationKonfirmPaswword = false
                } else if (binding.tfPassword.text.toString() == binding.tfKonfirmasiPassoword.text.toString()) {
                    binding.password.isErrorEnabled = false
                    binding.konfirmPassword.isErrorEnabled = false
                    validationPassword = true
                    validationKonfirmPaswword = true
                } else {
                    binding.password.isErrorEnabled = false
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.tfKonfirmasiPassoword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    binding.konfirmPassword.error = "Konfirmasi Password wajib diisi!"
                } else if (binding.tfPassword.text.toString() != binding.tfKonfirmasiPassoword.text.toString()) {
                    binding.konfirmPassword.error = "Konfirmasi password tidak sesuai!"
                    binding.password.isErrorEnabled = false
                    validationKonfirmPaswword = false
                } else if (binding.tfPassword.text.toString() == binding.tfKonfirmasiPassoword.text.toString()) {
                    binding.konfirmPassword.isErrorEnabled = false
                    validationKonfirmPaswword = true
                    validationPassword = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.buttonDaftar.setOnClickListener {
            if (binding.tfNamaLengkap.length() == 0) {
                binding.namaLengkap.error = "Nama lengkap wajib diisi"
            }

            if (binding.tfUsername.length() == 0) {
                binding.username.error = "Username wajib diisi"
            }

            if (binding.tfAlamatEmail.length() == 0) {
                binding.alamatEmail.error = "Email wajib diisi"
            }

            if (binding.tfPassword.length() == 0) {
                binding.password.error = "Password wajib diisi"
            }

            if (binding.tfKonfirmasiPassoword.length() == 0) {
                binding.konfirmPassword.error = "Konfirmasi password wajib diisi"
            }

            if (binding.rbLaki.isChecked) {
                validationJenisKelamin = true
            } else if (binding.rbPerempuan.isChecked) {
                validationJenisKelamin = true
            }

            Toast.makeText(
                context,
                "$validationNamaLengkap $validationUsername $validationEmail $validationPassword $validationKonfirmPaswword $validationJenisKelamin",
                Toast.LENGTH_SHORT
            ).show()

            if (validationNamaLengkap && validationUsername && validationEmail && validationPassword && validationKonfirmPaswword && validationJenisKelamin) {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }

        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}