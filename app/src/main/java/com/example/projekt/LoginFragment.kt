package com.example.projekt

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.projekt.Token.email
import com.example.projekt.databinding.FragmentLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    lateinit var retrofit: Retrofit
    lateinit var apiService: ApiService
    var userToken: String? = null
    var activity: Activity? = getActivity()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_login, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener { validateForm() }
        binding.newRegButton.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }
        //getAllScores()
    }

    fun login() {
        Toast.makeText(getActivity(), "Logowanie...", Toast.LENGTH_SHORT).show()
        val request = LoginRequest()
        //request.email = "fifi@fifi.pl"
        //request.password = "fifi"
        Log.e("email", binding.userInput.text.toString())
        Log.e("password", binding.passwordInput.text.toString())
        request.email = binding.userInput.text.toString()
        request.password = binding.passwordInput.text.toString()
        retrofit = ApiClient.getRetrofit()
        apiService = retrofit.create(ApiService::class.java)
        apiService.login(request).enqueue(object: Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("error login", t.message.toString())
                Toast.makeText(getActivity(), "Niepoprawne dane logowania!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val temp = response.body()
                if (temp != null){
                    Token.myToken = temp.token.toString()
                    Token.email = request.email.toString()
                    Token.username = temp.username.toString()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    //odnosimy sie do tokenu Token.myToken
                }
                else{
                    Toast.makeText(getActivity(), "Niepoprawne dane logowania!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }



    private fun validateForm() {
        if (binding.userInput.text.isEmpty()) {
            Toast.makeText(getActivity(), "Prosz?? poda?? Email!", Toast.LENGTH_SHORT).show()

        } else if (binding.passwordInput.text.isEmpty()) {
            Toast.makeText(getActivity(), "Prosz?? poda?? has??o!", Toast.LENGTH_SHORT).show()

        } else {
            login()
        }

    }
}