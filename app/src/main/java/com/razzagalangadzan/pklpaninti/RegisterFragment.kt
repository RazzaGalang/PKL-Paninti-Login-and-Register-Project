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
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.razzagalangadzan.pklpaninti.databinding.FragmentRegisterBinding
import java.util.regex.Pattern


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var _binding: FragmentRegisterBinding? = null;
    private val binding get() = _binding!!;

    var validationNamaLengkap       = "0"
    var validationUsername          = "0"
    var validationEmail             = "0"
    var validationPassword          = "0"
    var validationKonfirmPaswword   = "0"

    val minNameRegex = "^.{2,}$"
    val minUserRegex = "^.{6,}$"
    val validPassRegex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false);
        val view = binding.root;

        //TODO : Spannable Area and Different Color Text
        val spannable = SpannableStringBuilder(binding.textLogin.text.toString())
        val blueColor = ForegroundColorSpan(Color.parseColor("#55BCE0"))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val fragment = LoginFragment()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.flFragment,fragment)?.commit()
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
                if (!(s?.length ?: 0 >= 1)){
                    binding.namaLengkap.error = "Nama lengkap wajib diisi!"
                } else if (s.toString().matches(minNameRegex.toRegex())){
                    binding.namaLengkap.isErrorEnabled = false
                    validationNamaLengkap = "1"
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
                if (!(s?.length ?: 0 >= 1)){
                    binding.username.error = "Username wajib diisi!"
                } else if (s.toString().matches(minUserRegex.toRegex())){
                    binding.username.isErrorEnabled = false
                    validationUsername = "1"
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
                if (!(s?.length ?: 0 >= 1)){
                    binding.alamatEmail.error = "Email wajib diisi!"
                } else if (!isValidEmail(binding.tfAlamatEmail.text.toString())) {
                    binding.alamatEmail.error = "Format email tidak sesuai"
                } else {
                    binding.alamatEmail.isErrorEnabled = false
                    validationEmail = "1"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.tfPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)){
                    binding.password.error = "Password wajib diisi!"
                } else if (!(s.toString().matches(validPassRegex.toRegex()))){
                    binding.password.error = "Password minimal berisi 6 karakter, 1 huruf kapital dan 1 angka"
                    binding.konfirmPassword.error = "Konfirmasi password tidak sesuai!"
                    validationPassword = "0"
                    validationKonfirmPaswword ="0"
                } else if (binding.tfPassword.text.toString() != binding.tfKonfirmasiPassoword.text.toString() ){
                    binding.konfirmPassword.error = "Konfirmasi password tidak sesuai!"
                    binding.password.isErrorEnabled = false
                    validationPassword = "1"
                    validationKonfirmPaswword ="0"
                } else if (binding.tfPassword.text.toString() == binding.tfKonfirmasiPassoword.text.toString() ){
                    binding.password.isErrorEnabled = false
                    binding.konfirmPassword.isErrorEnabled = false
                    validationPassword = "1"
                    validationKonfirmPaswword ="1"
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
                if (!(s?.length ?: 0 >= 1)){
                    binding.konfirmPassword.error = "Konfirmasi Password wajib diisi!"
                } else if (binding.tfPassword.text.toString() != binding.tfKonfirmasiPassoword.text.toString() ){
                    binding.konfirmPassword.error = "Konfirmasi password tidak sesuai!"
                    binding.password.isErrorEnabled = false
                    validationKonfirmPaswword = "0"
                } else if (binding.tfPassword.text.toString() == binding.tfKonfirmasiPassoword.text.toString() ){
                    binding.konfirmPassword.isErrorEnabled = false
                    validationKonfirmPaswword = "1"
                    validationPassword = "1"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.buttonDaftar.setOnClickListener {
            if (binding.tfNamaLengkap.length() == 0){
                binding.namaLengkap.error = "Nama lengkap wajib diisi"
            }

            if (binding.tfUsername.length() == 0){
                binding.username.error = "Username wajib diisi"
            }

            if (binding.tfAlamatEmail.length() == 0){
                binding.alamatEmail.error = "Email wajib diisi"
            }

            if (binding.tfPassword.length() == 0){
                binding.password.error = "Password wajib diisi"
            }

            if (binding.tfKonfirmasiPassoword.length() == 0){
                binding.konfirmPassword.error = "Konfirmasi password wajib diisi"
            }

            if(binding.rbLaki.isChecked){
                //todo
            } else if (binding.rbPerempuan.isChecked) {
                //todo
            }

            Toast.makeText(context, "$validationNamaLengkap $validationUsername $validationEmail $validationPassword $validationKonfirmPaswword", Toast.LENGTH_SHORT).show()

            if (validationNamaLengkap != "0" && validationUsername != "0" && validationEmail != "0" && validationPassword != "0" && validationKonfirmPaswword != "0"){
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

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}