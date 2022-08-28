package com.example.projekt

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.projekt.databinding.FragmentRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class RegisterFragment : Fragment() {
    lateinit var retrofit: Retrofit
    lateinit var apiService: ApiService
    private lateinit var binding: FragmentRegisterBinding
    //private lateinit var binding: Register
    var activity: Activity? = getActivity()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_register, container, false)
        //binding = Regis
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerButton.setOnClickListener { validateForm() }
        //binding.newRegButton.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }
    }
    private fun validateForm() {
        Log.e("username!", binding.userRegInput.text.toString())
        Log.e("email!", binding.emailRegInput.text.toString())
        Log.e("pass1!", binding.passwordRegInput.text.toString())
        Log.e("pass1!", binding.confPassRegInput.text.toString())
        if (binding.userRegInput.text.isEmpty()) {
            Toast.makeText(getActivity(), "Proszę podać nazwę użytkownika!", Toast.LENGTH_SHORT).show()

        } else if (binding.emailRegInput.text.isEmpty()) {
            Toast.makeText(getActivity(), "Proszę podać hasło!", Toast.LENGTH_SHORT).show()

        } else if (binding.passwordRegInput.text.isEmpty()) {
            Toast.makeText(getActivity(), "Proszę podać hasło!", Toast.LENGTH_SHORT).show()

        } else if (binding.confPassRegInput.text.isEmpty()) {
            Toast.makeText(getActivity(), "Proszę powtórzyć hasło!", Toast.LENGTH_SHORT).show()

        }else {
            if (binding.passwordRegInput.text.toString() == binding.confPassRegInput.text.toString()){
                register()
            }
            else{
                Toast.makeText(getActivity(), "Hasła nie są takie same!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun register() {
        Toast.makeText(getActivity(), "Rejestrowanie...", Toast.LENGTH_SHORT).show()
        val request = RegisterRequest()
        request.username = binding.userRegInput.text.toString()
        request.email = binding.emailRegInput.text.toString()
        request.password = binding.passwordRegInput.text.toString()
        retrofit = ApiClient.getRetrofit()
        apiService = retrofit.create(ApiService::class.java)
        apiService.register(request).enqueue(object: Callback<RegisterResponse> {
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
               // Log.e("error register!", t.message.toString())
                Toast.makeText(getActivity(), "Niepoprawne dane rejestracji!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                val user = response.body()
                //if (user != null)
                Log.e("Zarejestrowany email", user.toString())
                if (user != null) {
                    Log.e("Zarejestrowany email", user!!.email.toString())
                    Log.e("Zarejestrowany id", user!!.userId.toString())
                    Log.e("Zarejestrowany username", user!!.username.toString())
                    //tutaj powrót do fragmentu login
                    findNavController().popBackStack(R.id.loginFragment, false)
                }else{
                    Log.e("Zarejestrowany email", user.toString())
                    Toast.makeText(getActivity(), "Email/Username jest już zajęty!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}



