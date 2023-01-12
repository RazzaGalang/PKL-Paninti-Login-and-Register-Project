package com.razzagalangadzan.pklpaninti

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.razzagalangadzan.pklpaninti.databinding.FragmentLoginBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
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

    private var _binding:FragmentLoginBinding? = null;
    private val binding get() = _binding!!;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false);
        val view = binding.root;

        //TODO : Spannable Area and Different Color Text
        val spannable = SpannableStringBuilder(binding.textRegister.text.toString())
        val blueColor = ForegroundColorSpan(Color.parseColor("#55BCE0"))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val fragment = RegisterFragment()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.flFragment,fragment)?.commit()
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
                if (binding.tfEmailAtauUsername.length() == 0){
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
                if (binding.tfPasswordLogin.length() == 0){
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
            if (binding.tfEmailAtauUsername.length() == 0 && binding.tfPasswordLogin.length() == 0 ){
                binding.emailAtauUsername.error = "Email atau Username wajib diisi"
                binding.passwordLogin.error = "Password wajib diisi"
            } else if (binding.tfEmailAtauUsername.length() == 0){
                binding.emailAtauUsername.error = "Email atau Username wajib diisi"
            } else if (binding.tfPasswordLogin.length() == 0){
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}