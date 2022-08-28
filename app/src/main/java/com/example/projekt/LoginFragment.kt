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
import com.example.projekt.databinding.FragmentLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentLoginBinding
    private var param1: String? = null
    private var param2: String? = null
    lateinit var retrofit: Retrofit
    lateinit var apiService: ApiService
    //var userToken: LoginResponse? = null
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
                Log.e("Login token", temp.toString())
                if (temp != null){
                    userToken = temp.token.toString()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                else{
                    Toast.makeText(getActivity(), "Niepoprawne dane logowania!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun validateForm() {
        if (binding.userInput.text.isEmpty()) {
            Toast.makeText(getActivity(), "Proszę podać Email!", Toast.LENGTH_SHORT).show()

        } else if (binding.passwordInput.text.isEmpty()) {
            Toast.makeText(getActivity(), "Proszę podać hasło!", Toast.LENGTH_SHORT).show()

        } else {
            login()
        }

    }
}